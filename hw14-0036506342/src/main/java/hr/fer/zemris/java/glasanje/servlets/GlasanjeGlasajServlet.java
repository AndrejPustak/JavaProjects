package hr.fer.zemris.java.glasanje.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Thic class represents a servlet that is called when a vote is placed.
 * @author Andrej
 *
 */
@WebServlet(name="glasanjeGlasaj", urlPatterns = "/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = Long.valueOf(req.getParameter("id"));
		DAOProvider.getDao().updateVote(id);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");

		
	}
}
