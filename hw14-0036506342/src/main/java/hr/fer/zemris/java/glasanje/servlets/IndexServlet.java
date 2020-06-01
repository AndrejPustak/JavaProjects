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

/**
 * Webservlet for the voting-app that forwards the requests to index.jsp to show all
 * the available polls for choosing.
 * @author Andrej
 *
 */
@WebServlet(name="index", urlPatterns="/servleti/index.html")
public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<PollInput> inputs = DAOProvider.getDao().getPolls();
		
		req.setAttribute("inputs", inputs);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		
	}
}
