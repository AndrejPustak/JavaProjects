package hr.fer.zemris.java.web.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This is a ServletContextListeners that at the start of the webapp saved the current system time.
 * @author Andrej
 *
 */
@WebListener
public class TimeListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
