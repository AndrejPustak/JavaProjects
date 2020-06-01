package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet sets the color of the webapp for this session to the color given by the 
 * parameter pickedBgCol.
 * @author Andrej
 *
 */
@WebServlet(name = "setColor", urlPatterns={"/setcolor"})
public class SetColor extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = null;
		if(req.getParameter("pickedBgCol") != null) {
			color = String.valueOf(req.getParameter("pickedBgCol"));
		}
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
		
	}
}
