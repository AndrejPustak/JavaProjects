package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("text/html");
		try {
			context.write("<html><body>");
			context.write("<table border=1>");
			context.write("<thead>");
			context.write("<tr><th>Name</th><th>Value</th></tr>");
			context.write("</thead>");
			for(String name : context.getParameterNames()) {
				context.write("<tr>");
				context.write("<th>"+name+"</th>");
				context.write("<th>"+context.getParameter(name)+"</th>");
				context.write("</tr>");
			}
			context.write("</table>");
			context.write("</body></html>");
			context.flush();
		} catch (IOException ex) {
// Log exception to servers log...
			ex.printStackTrace();
		}
		
	}

}
