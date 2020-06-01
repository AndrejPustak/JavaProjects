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
 * This servlet represents a poll to vote for one of the options for the selected poll.
 * The poll information is taken form a database.
 * @author Andrej
 *
 */
@WebServlet(name="glasanje", urlPatterns = "/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = Long.valueOf(req.getParameter("pollID"));
		List<PollOptionInput> inputs = DAOProvider.getDao().getPollOptions(id);
		PollInput poll = DAOProvider.getDao().getPoll(id);
		
		req.getSession().setAttribute("poll", poll);
		req.setAttribute("inputs", inputs);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
