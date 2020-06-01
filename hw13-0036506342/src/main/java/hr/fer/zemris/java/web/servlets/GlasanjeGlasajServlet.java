package hr.fer.zemris.java.web.servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Inherited;
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
 * Thic class represents a servlet that is called when a vote is placed.
 * @author Andrej
 *
 */
@WebServlet(name="glasanjeGlasaj", urlPatterns = "/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);
		
		String id = req.getParameter("id");
		
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
		
		if(results.containsKey(id)) {
			results.put(id, results.get(id) + 1);
		} else {
			results.put(id, 1);
		}
		
		try(OutputStream os = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING))){
			for(String rid : results.keySet()) {
				String line = "" + rid + "\t" + results.get(rid) + "\r\n";
				os.write(line.getBytes());
			}
			os.flush();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

		
	}
}
