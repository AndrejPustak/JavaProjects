package hr.fer.zemris.java.glasanje.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollInput;
import hr.fer.zemris.java.p12.model.PollOptionInput;

/**
 * This servlet is used to show the results of the voting.
 * @author Andrej
 *
 */
@WebServlet(name="glasanjeRezultati", urlPatterns = "/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PollInput poll = (PollInput) req.getSession().getAttribute("poll");
		List<PollOptionInput> inputs = DAOProvider.getDao().getPollOptions(poll.getId());
		
		req.getSession().setAttribute("inputs", inputs);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

		
	}
}
