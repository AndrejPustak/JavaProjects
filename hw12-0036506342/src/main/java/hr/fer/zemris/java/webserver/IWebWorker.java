package hr.fer.zemris.java.webserver;

/**
 * This class represents a web worker that processes web requests
 * @author Andrej
 *
 */
public interface IWebWorker {
	
	/**
	 * This method processes a web request
	 * @param context Request's context
	 * @throws Exception if the request process was not successful
	 */
	public void processRequest(RequestContext context) throws Exception;
}
