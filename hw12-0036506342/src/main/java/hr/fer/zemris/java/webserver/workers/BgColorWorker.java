package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		boolean updated = false;
		context.setMimeType("text/html");
		
		if(context.getParameterNames().contains("bgcolor")) {
			String color = context.getParameter("bgcolor");
			if(color.length() == 6 && color.matches("-?[0-9a-fA-F]+")) {
				context.setPersistentParameter("bgcolor", color);
				updated = true;
			}
		}
		
		try {
			context.write("<html><body>");
			if (updated) {
				context.write("<p>The bgcolor was updated!</p>");
			} else {
				context.write("<p>The bgcolor was not updated!</p>");
			}
			context.write("<a href=index2.html> index2.html </a>");
			context.write("</body></html>");
			context.flush();
		} catch (IOException ex) {
// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}
