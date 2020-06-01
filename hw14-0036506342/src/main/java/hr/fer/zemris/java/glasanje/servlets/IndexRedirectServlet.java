package hr.fer.zemris.java.glasanje.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Webservlet for the voting-app that redirects all request from /index.html to 
 * servleti/index.html.
 * @author Andrej
 *
 */
@WebServlet(name="indexRedirect", urlPatterns="/index.html")
public class IndexRedirectServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
		
	}
}
