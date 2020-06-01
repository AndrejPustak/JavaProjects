package hr.fer.zemris.java.glasanje.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.model.PollOptionInput;

/**
 * THis servlet generates a pie-chart representing the number of votes for each of the options.
 * @author Andrej
 *
 */
@WebServlet(name = "glasanjeGrafika", urlPatterns={"/servleti/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png; charset=UTF-8");
		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset);
		BufferedImage image = chart.createBufferedImage(400, 400, BufferedImage.TYPE_INT_RGB, null);
		try {
			ImageIO.write(image, "png", resp.getOutputStream());
		} catch(IOException exc) {
			exc.printStackTrace();
		}
	}
	
	/**
	 * THis method creates a dataset for this pie-chart
	 * @param req servlet request
	 * @return created dataset
	 */
	@SuppressWarnings("unchecked")
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		List<PollOptionInput> inputs = (List<PollOptionInput>) req.getSession().getAttribute("inputs");
		
		for(PollOptionInput input : inputs) {
			if(input.getVotesCount() != 0)
				dataset.setValue(input.getOptionTitle(), input.getVotesCount());
		}
		return dataset;
	}

	/**
	 * THis method creates the pie-chart from the given dataset
	 * @param dataset dataset for the pie-chart
	 * @return created pie-chart
	 */
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Chart", dataset);
		chart.setTitle("Rezultati glasanja");
		
		return chart;
	}

}
