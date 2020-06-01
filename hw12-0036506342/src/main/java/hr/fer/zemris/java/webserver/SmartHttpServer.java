package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that represents a smart HTTP server.
 * @author Andrej
 *
 */
public class SmartHttpServer {
	
	/**
	 * Value in ms for the interval between removing expired sessions
	 */
	private final int EXPIRED_SESSIONS_REMOVE = 30000; 
	
	/**
	 * Server's address
	 */
	private String address;
	/**
	 * Server's domain name
	 */
	private String domainName;
	/**
	 * Server's port
	 */
	private int port;
	/**
	 * Number of server's worker threads
	 */
	private int workerThreads;
	/**
	 * Value in ms after which the session will timeout
	 */
	private int sessionTimeout;
	/**
	 * Map of all available extensions and their mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool
	 */
	private ExecutorService threadPool;
	/**
	 * Reference to the servers root directory
	 */
	private Path documentRoot;
	
	/**
	 * Map of all the workers mapped to their urlPaths
	 */
	private Map<String, IWebWorker> workersMap;
	
	/**
	 * Map of all the sessions mapped to the SID
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Object for creating random sessions id's
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Constructor for the SmartHttpServer
	 * @param configFileName path to the config file
	 */
	public SmartHttpServer(String configFileName) {
		Path configPath = Paths.get(configFileName);
		
		Properties prop = new Properties();
		try {
			prop.load(Files.newInputStream(configPath));
		} catch (IOException e) {
			System.out.println("Unable to read the properties");
		}
		
		address = prop.getProperty("server.address");
		domainName = prop.getProperty("server.domainName");
		port = Integer.parseInt(prop.getProperty("server.port"));
		workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout")) * 1000 ;
		
		documentRoot = Paths.get("./webroot");
		
		Thread sessionThread = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(300000);
				} catch(Exception ex) {}
				SwingUtilities.invokeLater(()->{
					removeExpiredSessions();
				});
			}
		});
		sessionThread.setDaemon(true);
		sessionThread.start();
		
		configureWorkers(prop.getProperty("server.workers"));
		configureMimeTypes(prop.getProperty("server.mimeConfig"));
		
	}
	
	/**
	 * Method that removed the expired session from sessions map
	 * It is called by a daemon thread by default every 5 minutes
	 */
	private void removeExpiredSessions() {
		String[] keySet = new String[sessions.keySet().size()];
		int i = 0;
		for(String key : sessions.keySet()) {
			keySet[i] = key;
			i++;
		}
		
		for(String sid : keySet) {
			if(sessions.get(sid).validUntil < System.currentTimeMillis()) {
				sessions.remove(sid);
			}
		}
	}

	/**
	 * This method is used to configure workersMap
	 * @param workerPath path to the worker config file
	 */
	@SuppressWarnings("deprecation")
	private void configureWorkers(String workerPath) {
		workersMap = new ConcurrentHashMap<String, IWebWorker>();
		Path workerConfig = Paths.get(workerPath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(workerConfig);
		} catch (IOException e) {
			System.out.println("Unable to read lines");
		}
		
		for(String line : lines) {
			if(line.strip().startsWith("#")) continue;
			
			String[] lineA = line.split("=");
			String path = lineA[0].strip();
			if(workersMap.containsKey(path)) {
				throw new IllegalArgumentException("Multiple lines with the same path");
			}
			String fqcn = lineA[1].strip();
			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to load the worker class");
			}
			Object newObject = null;
			try {
				newObject = referenceToClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println("Unable to create a new instance of the class");
			}
			IWebWorker iww = (IWebWorker)newObject;
			
			workersMap.put(path, iww);
			
		}
		
	}
	
	/**
	 * This method is used to configure the mimeTypes map
	 * @param path path to the mime config file
	 */
	private void configureMimeTypes(String path) {
		Path mimePath = Paths.get(path);
		
		Properties mimeProp = new Properties();
		try {
			mimeProp.load(Files.newInputStream(mimePath));
		} catch (IOException e) {
			System.out.println("Unable to read the mime config");
		}
		
		for(Object key : mimeProp.keySet()) {
			mimeTypes.put((String) key, mimeProp.getProperty((String) key));
		}
		
	}
	
	/**
	 * Method that starts the server thread and configures the thread pool
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		
		if(serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			serverThread.run();
		}
		
	}
	
	/**
	 * Method that stops the server thread and shuts down the thread pool
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		threadPool.shutdown();
	}
	
	/**
	 * Class that represents the server thread
	 * @author Andrej
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Flag that when is set to true the thread will terminate
		 */
		private boolean terminate;
		
		/**
		 * Constructor for the server thread
		 */
		public ServerThread() {
			terminate = false;
		}
		
		@Override
		public void run() {
			ServerSocket sSocket = null;
			try {
				sSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Unable to open the server socket");
			}
			
			while(!terminate) {
				Socket cSocket = null;
				try {
					cSocket = sSocket.accept();
				} catch (IOException e) {
					System.out.println("Unable to open the client socket");
				}
				
				ClientWorker cw = new ClientWorker(cSocket);
				threadPool.submit(cw);
			}
		}
		
		/**
		 * Method used to terminate the server thread
		 */
		public void terminate() {
			terminate = true;
		}
	}
	
	/**
	 * Class that represents a client worker
	 * @author Andrej
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher{
		/**
		 * Client socket
		 */
		private Socket csocket;
		/**
		 * Client input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Client output stream
		 */
		private OutputStream ostream;
		/**
		 * Version
		 */
		private String version;
		/**
		 * Method
		 */
		private String method;
		/**
		 * Host address
		 */
		private String host;
		/**
		 * Map of all the parameters and their names
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Map of all the temporary parameters and their names
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Map of all the persistent parameters and their names
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * List of all the output cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Client session ID
		 */
		private String SID;
		/**
		 * Clients request context
		 */
		private RequestContext context = null;

		/**
		 * Constructor for the client worker
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(
						csocket.getInputStream()
						);
				ostream = new BufferedOutputStream(
						csocket.getOutputStream()
				);
				
				byte[] request = readRequest(istream);
				if(request==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestStr = new String(
					request, 
					StandardCharsets.US_ASCII
				);
				
				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? 
					null : headers.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 400, "Method Not Allowed");
					return;
				}
				
				String version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 400, "HTTP Version Not Supported");
					return;
				}
				
				for(String header : headers) {
					if(header.startsWith("Host:")) {
						host = getHostName(header.substring(5).trim());
					}
				}
				
				if(host == null) {
					host = domainName;
				}
				
				checkSession(headers);
				
				String path;
				String paramString = "";
				
				String[] pathAndParam = firstLine[1].split("\\?");
				path = pathAndParam[0];
				
				if(pathAndParam.length == 2) {
					paramString = pathAndParam[1];
				}
				parseParameters(paramString);
			
				internalDispatchRequest(path, true);
				
			} catch(Exception ex) {
				System.out.println("Pogreška: " + ex.getMessage());
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					System.out.println("Unable to close the socket");
				}
			}
		}

		/**
		 * Method that checks the header if there is a cookie with
		 * name "sid" and loads it's parameters
		 * @param headers
		 */
		private synchronized void checkSession(List<String> headers) {
			String sidCandidate = "";
			
			for(String header : headers) {
				if(!header.startsWith("Cookie:")) {
					continue;
				}
				
				String rest = header.substring(7);
				String[] cookies = rest.split(";");
				
				for(String cookie : cookies) {
					String[] nameAndValue = cookie.split("=");
					if(nameAndValue[0].strip().equals("sid")) {
						sidCandidate = nameAndValue[1].substring(1, nameAndValue[1].length() - 1);
					}
				}
			}
			
			if(sidCandidate.equals("") || !sessions.containsKey(sidCandidate)) {
				SID = generateSID();
				
				SessionMapEntry entry = new SessionMapEntry(
						SID,
						host,
						System.currentTimeMillis() + sessionTimeout,
						new ConcurrentHashMap<String, String>()
						);
				sessions.put(SID, entry);
				outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
				permPrams = entry.map;
			}
			else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if(!entry.host.equals(host)) {
					permPrams = entry.map;
					return;
				}
				if(entry.validUntil < System.currentTimeMillis()) {
					sessions.remove(sidCandidate);
					permPrams = entry.map;
					return;
				}
				
				entry.validUntil = sessionTimeout + System.currentTimeMillis();
				permPrams = entry.map;
			}
			
		}

		/**
		 * Method that is used to generate a random session ID of 20 upper case letters
		 * @return random session ID
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i<20; i++) {
				sb.append((char)sessionRandom.nextInt(26) + 65);
			}
			
			return sb.toString();
		}

		/**
		 * Method that determines mime type based on an extension
		 * @param ext file extension
		 * @return mime type mapped to the extension, "application/octet-stream" otherwise
		 */
		private String determineMimeType(String ext) {
			if(mimeTypes.containsKey(ext)) {
				return mimeTypes.get(ext);
			}
			
			return "application/octet-stream";
			
		}

		/**
		 * Method that is used to serve the requested file
		 * @param rc request's context
		 * @param requestedFile path to the requested file
		 */
		private void serveFile(RequestContext rc, Path requestedFile) {
			
			try(InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))){
					byte[] buf = new byte[1024];
					while(true) {
						int r = is.read(buf);
						if(r<1) break;
						rc.write(buf, 0, r);
					}
					rc.flush();
			} catch (IOException e) {
				System.out.println("Unable to serve the file");
			}
			
		}

		/**
		 * This method is used to parse the parameters
		 * @param paramString string of the parameters separated by '&'
		 */
		private void parseParameters(String paramString) {
			if(paramString.equals("")) return;
			
			String[] params = paramString.split("&");
			
			for(String param : params) {
				String[] paramA = param.split("=");
				if(paramA.length != 2) continue;
				this.params.put(paramA[0], paramA[1]);
			}
		}

		/**
		 * This method gets the host address by removing the port number after the host
		 * address
		 * @param name
		 * @return
		 */
		private String getHostName(String name) {
			return name.split(":")[0];
		}

		/**
		 * This method is used to extract the headers from the given header text
		 * @param requestHeader header text you wish to extract the headers from
		 * @return List of extracted headers
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Method that is used to write an error to the given output stream
		 * @param cos cilent's output stream
		 * @param statusCode error's status code
		 * @param statusText error text
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) {
			try {
				cos.write(
						("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
						"Server: simple java server\r\n"+
						"Content-Type: text/plain;charset=UTF-8\r\n"+
						"Content-Length: 0\r\n"+
						"Connection: close\r\n"+
						"\r\n").getBytes(StandardCharsets.US_ASCII)
					);
				
				cos.flush();
			} catch (IOException e) {
				System.out.println("Unable to send error to the given outputstream");
			}
				
			
		}

		/**
		 * Method used to read the request and extract the header text
		 * @param is input stream
		 * @return request's header text as a byteArray
		 */
		private byte[] readRequest(InputStream is) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = 0;
				try {
					b = is.read();
				} catch (IOException e) {
					System.out.println("Unable to read from input stream");
				}
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
			
		}
		
		/**
		 * Method used to dispatch the worker request
		 * @param urlPath url path of the request
		 * @param directCall true if the request is direct from the browser, false otherwise
		 * @throws Exception if the request dispatch was not successful
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception{
			Path requestedFile = documentRoot.resolve(urlPath.substring(1));
			if(!requestedFile.normalize().startsWith(documentRoot.normalize())) {
				sendError(ostream, 403, "Forbiden");
			}
			
			if(requestedFile.normalize().startsWith(documentRoot.resolve("private").normalize()) && directCall == true) {
				sendError(ostream, 404, "Unable to make a direct call to /private");
			}
			
			String mimeType = "";
			String ext;
			
			if(urlPath.startsWith("/ext/")) {
				if(context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				String workerName = urlPath.substring(5);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." + workerName);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(context);
				
				return;
			}
			
			if(workersMap.containsKey(urlPath)) {
				if(context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				workersMap.get(urlPath).processRequest(context);
				return;
			}
			
			if(!Files.isReadable(requestedFile)){
				sendError(ostream, 404, "File is not readable");
				return;
			}else {
				String name = requestedFile.getFileName().toString();
				ext = name.substring(name.lastIndexOf('.') + 1);
				mimeType = determineMimeType(ext);
			}
			
			if(ext.equals("smscr")) {
				if(context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				context.setMimeType("text/plain");
				context.setStatusCode(200);
				
				String text = Files.readString(requestedFile);
				SmartScriptParser parser = new SmartScriptParser(text);
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
				
				engine.execute();
				context.flush();
			} else {
				if(context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				context.setMimeType(mimeType);
				context.setStatusCode(200);
				
				serveFile(context, requestedFile);
			}
			
		}
	}
	
	/**
	 * Entry in the sessions map 
	 * @author Andrej
	 *
	 */
	private static class SessionMapEntry {
		@SuppressWarnings("unused")
		/**
		 * Session ID
		 */
		String sid;
		/**
		 * Host address
		 */
		String host;
		/**
		 * Time in ms until the request is valid
		 */
		long validUntil;
		/**
		 * Map of the parameters
		 */
		Map<String, String> map;
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
		
		
	}

	/**
	 * Method from which the server starts
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected path to server.properties file");
			return;
		}
		
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();

	}
}
