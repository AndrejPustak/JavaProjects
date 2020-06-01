package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet represents a poll to vote for your favourite band.
 * The band information is taken from a file in WEB-INF folder.
 * The ID, name and link are separated by tab.
 * @author Andrej
 *
 */
@WebServlet(name="glasanje", urlPatterns = "/glasanje")
public class GlasanjeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path path = Paths.get(fileName);
		
		Map<String, String> bandNames = new HashMap<String, String>();
		Map<String, String> bandLinks = new HashMap<String, String>();
		List<String> lines = Files.readAllLines(path);
		for(String line : lines) {
			String[] sLine = line.split("\t");
			bandNames.put(sLine[0].strip(), sLine[1].strip());
			bandLinks.put(sLine[0].strip(), sLine[2].strip());
		}
		
		req.getSession().setAttribute("names", bandNames);
		req.getSession().setAttribute("links", bandLinks);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
