package hr.fer.zemris.java.web.servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to show the results of the voting.
 * @author Andrej
 *
 */
@WebServlet(name="glasanjeRezultati", urlPatterns = "/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);
		
		Map<String, Integer> results = new HashMap<>();
		
		if(Files.isRegularFile(path)) {
			List<String> lines = Files.readAllLines(path);
			if(lines.size() != 0) {
				for(String line : lines) {
					String[] sLine = line.split("\t");
					results.put(sLine[0].strip(), Integer.parseInt(sLine[1].strip()));
				}
			}
		} else {
			Files.createFile(path);
		}
		
		req.setAttribute("results", results);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

		
	}
}
