package hr.fer.zemris.java.webserver;

/**
 * Interface that represents a dispatcher for requests
 * @author Andrej
 *
 */
public interface IDispatcher {
	/**
	 * This method is used to dispatch a request
	 * @param urlPath urlPath of the request
	 * @throws Exception If the dispatch was not successful
	 */
	void dispatchRequest(String urlPath) throws Exception;
}