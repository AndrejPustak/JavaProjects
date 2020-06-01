package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents context of a request. It also contains write
 * methods for writing to the requests's output stream.
 * THe first time any of the write methods is called a header is generated and
 * written before any of the data.
 * @author Andrej
 *
 */
public class RequestContext {
	
	/**
	 * Default encoding value
	 */
	private final String DEFAULT_ECODING = "UTF-8";
	/**
	 * Default status text value
	 */
	private final String DEFAULT_STATUS_TEXT = "OK";
	/**
	 * Default mime type value
	 */
	private final String DEFAULT_MIME_TYPE ="text/html";
	/**
	 * Default status code
	 */
	private final int DEFAUTL_STATUS_CODE = 200;
	
	/**
	 * Output stream
	 */
	private OutputStream outputStream;
	/**
	 * Charset to be used in output stream
	 */
	private  Charset charset;
	
	/**
	 * Current encoding type
	 */
	private String encoding;
	/**
	 * Current status code
	 */
	private int statusCode;
	/**
	 * Current status text
	 */
	private String statusText;
	/**
	 * Current mime type
	 */
	private String mimeType;
	/**
	 * Current content length
	 */
	private Long contentLength;
	
	/**
	 * Map containing all request parameters
	 */
	private Map<String, String> parameters;
	/**
	 * Map containing all request's temporary parameters
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Map containing all request's persistent parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List containing all request's output cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Request's dispatcher
	 */
	private IDispatcher dispatcher;
	/**
	 * Request's Session ID
	 */
	private String SID;
	
	/**
	 * Flag that keeps track if the header has been generated
	 */
	private boolean headerGenerated;
	
	/**
	 * Constructor for the RequestContext
	 * @param outputStream request's output stream
	 * @param parameters request's parameters
	 * @param persistentParameters request's persistent parameters
	 * @param outputCookies request's output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		super();
		this.outputStream = Objects.requireNonNull(outputStream);
		if(parameters == null)
			this.parameters = new HashMap<>();
		else
			this.parameters = parameters;
		if(persistentParameters == null)
			this.persistentParameters = new HashMap<>();
		else
			this.persistentParameters = persistentParameters;
		if(outputCookies == null)
			this.outputCookies = new ArrayList<>();
		else
			this.outputCookies = outputCookies;
		
		temporaryParameters = new HashMap<String, String>();
		
		encoding = DEFAULT_ECODING;
		statusCode = DEFAUTL_STATUS_CODE;
		statusText = DEFAULT_STATUS_TEXT;
		mimeType = DEFAULT_MIME_TYPE;
		contentLength = null;
		
		headerGenerated = false;
		SID = "";
	}
	
	/**
	 * Constructor for the RequestContext
	 * @param outputStream request's output stream
	 * @param parameters request's parameters
	 * @param persistentParameters request's persistent parameters
	 * @param outputCookies request's output cookies
	 * @param temporaryParameters request's temporary parameters
	 * @param dispatcher Request's dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters,
			IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		
		if(temporaryParameters == null)
			this.temporaryParameters = new HashMap<>();
		else
			this.temporaryParameters = temporaryParameters;
		
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Constructor for the RequestContext
	 * @param outputStream request's output stream
	 * @param parameters request's parameters
	 * @param persistentParameters request's persistent parameters
	 * @param outputCookies request's output cookies
	 * @param temporaryParameters request's temporary parameters
	 * @param dispatcher Request's dispatcher
	 * @param SID Request's Session ID 
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters,
			IDispatcher dispatcher, String SID) {
		this(outputStream, parameters, persistentParameters, outputCookies, temporaryParameters, dispatcher);
		
		this.SID = SID;
	}
	
	/**
	 * This class represents a single cookie
	 * @author Andrej
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookie's name
		 */
		private String name;
		/**
		 * Cookie's value
		 */
		private String value;
		/**
		 * Cookie's domain
		 */
		private String domain;
		/**
		 * Cookie's path
		 */
		private String path;
		
		/**
		 * Cookie's maxAge
		 */
		private Integer maxAge;
		
		/**
		 * Constructor for RCCookie
		 * @param name Cookie's name
		 * @param value Cookie's value
		 * @param maxAge Cookie's maxAge
		 * @param domain Cookie's domain
		 * @param path Cookie's path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Getter for cookie's name
		 * @return cookie's name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Getter for cookie's value
		 * @return cookie's value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for cookie's domain
		 * @return cookie's domain
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Getter for cookie's path
		 * @return cookie's path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Getter for cookie's max age
		 * @return cookie's max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}

	/**
	 * Getter for a parameter's value with the given name
	 * @param name name of the parameter
	 * @return value of the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Getter for the parameter names
	 * @return Set of parameter names
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Getter for a persistent parameter's value with the given name
	 * @param name name of the persistent parameter
	 * @return value of the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Getter for the persistent parameter names
	 * @return Set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Setter for one entry of persistent parameter map
	 * @param name Name of the persistent parameter
	 * @param value Value of the persistent parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * This method removes one entry from the persistent parameter
	 * map with the given value.
	 * @param name Name of the parameter you wish to remove
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Getter for a temporary parameter's value with the given name
	 * @param name name of the temporary parameter
	 * @return value of the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Getter for the temporary parameter names
	 * @return Set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Getter for the session ID
	 * @return Request's session ID
	 */
	public String getSessionID() {
		return SID;
	}
	
	/**
	 * Setter for one entry of temporary parameter map
	 * @param name Name of the temporary parameter
	 * @param value Value of the temporary parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * This method removes one entry from the temporary parameter
	 * map with the given value.
	 * @param name Name of the parameter you wish to remove
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * THis method is used to write to the request's output stream
	 * @param data data you wish to write
	 * @return reference to this RequestContext
	 * @throws IOException If the writing was not successful
	 */
	public RequestContext write(byte[] data) throws IOException{
		if(!headerGenerated) {
			String header = generateHeader();
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			headerGenerated = true;
		}
		outputStream.write(data);
		
		return this;
	}
	
	/**
	 * This method is used to write to the reques'ts output stream
	 * at the given offset for the given length
	 * @param data data you wish to write
	 * @param offset offset of which you wish to write
	 * @param len length of the data you wish to write
	 * @return reference to this RequestContext
	 * @throws IOException If the writing was not successful
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException{
		if(!headerGenerated) {
			String header = generateHeader();
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			headerGenerated = true;
		}
		
		outputStream.write(data, offset, len);
		
		return this;
	}
	
	/**
	 * THis method is used to write to the request's output stream
	 * @param text Text you wish to write
	 * @return reference to this RequestContext
	 * @throws IOException If the writing was not successful
	 */
	public RequestContext write(String text) throws IOException{
		if(!headerGenerated) {
			String header = generateHeader();
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			headerGenerated = true;
		}
		outputStream.write(text.getBytes(charset));
		
		return this;
	}
	
	/**
	 * THis method is used to generate the header for the request
	 * @return generated header
	 */
	private String generateHeader() {
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		
		//FIRST LINE
		sb.append("HTTP/1.1");
		sb.append(" " + statusCode);
		sb.append(" " + statusText);
		sb.append("\r\n");
		
		//SECOND LINE
		sb.append("Content-Type: ");
		sb.append(mimeType);
		if(mimeType.startsWith("text/"))
			sb.append("; charset=" + encoding);
		sb.append("\r\n");
		
		//THIRID LINE
		if(contentLength != null){
			sb.append("Content-Length: " + contentLength);
			sb.append("\r\n");
		}
		
		//FOURTH LINE
		if(outputCookies.size() != 0) {
			for(RCCookie cookie : outputCookies) {
				sb.append(String.format("Set-Cookie: %s=\"%s\"", cookie.getName(), cookie.getValue(), cookie.getDomain()));
				if(cookie.getDomain() != null) {
					sb.append("; Domain=" + cookie.getDomain());
				}
				if(cookie.getPath() != null) {
					sb.append("; Path=" + cookie.getPath());
				}
				if(cookie.getMaxAge() != null) {
					sb.append("; Max-Age=" + cookie.getMaxAge().toString());
				}
				if(cookie.name.equals("sid")) {
					sb.append("; HttpOnly");
				}
				sb.append("\r\n");
			}
		}
		
		sb.append("\r\n");
		
		return sb.toString();
		
	}
	
	/**
	 * Setter for the encoding type
	 * @param encoding encoding type
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated)
			throw new RuntimeException("Header already generated.");
		this.encoding = encoding;
	}
	
	/**
	 * Setter for the status code
	 * @param statusCode status code
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated)
			throw new RuntimeException("Header already generated.");
		this.statusCode = statusCode;
	}

	/**
	 * Setter for the status text
	 * @param statusText status text
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated)
			throw new RuntimeException("Header already generated.");
		this.statusText = statusText;
	}

	/**
	 * Setter for the mime type
	 * @param mimeType mime type
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated)
			throw new RuntimeException("Header already generated.");
		this.mimeType = mimeType;
	}
	
	/**
	 * Setter for the content length
	 * @param contentLength content length
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated)
			throw new RuntimeException("Header already generated.");
		this.contentLength = contentLength;
	}
	
	/**
	 * This method is used to add a new cookie to the list
	 * @param rcCookie cookie you wish to add to the list
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
	
	/**
	 * THis method is used to flush the output stream
	 */
	public void flush() {
		try {
			outputStream.flush();
			headerGenerated = false;
		} catch (IOException e) {
			System.out.println("unable to flush the outputstream");
		}
	}
	
	/**
	 * Getter for the request's dispatcher
	 * @return request's dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
}
