package hr.fer.zemris.jsdemo.servlets;

import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class represents a web listener that is used to configure the picture database.
 * @author Andrej
 *
 */
@WebListener
public class Initalization implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PictureDB.getDB().configureDB(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt")));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
