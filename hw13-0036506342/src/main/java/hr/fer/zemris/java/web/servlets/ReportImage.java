package hr.fer.zemris.java.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

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

/**
 * This servlet generates a pie chart representing OS usage survey results.
 * @author Andrej
 *
 */
@WebServlet(name = "reportImage", urlPatterns={"/reportImage"})
public class ReportImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png; charset=UTF-8");
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		BufferedImage image = chart.createBufferedImage(500, 500, BufferedImage.TYPE_INT_RGB, null);
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
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Win10", 52.9);
		dataset.setValue("Win8", 4.7);
		dataset.setValue("Win7", 16.3);
		dataset.setValue("WinXP", 0.2);
		dataset.setValue("Linux", 5.7);
		dataset.setValue("Mac", 10.6);
		
		return dataset;
	}

	/**
	 * THis method creates the pie-chart from the given dataset
	 * @param dataset dataset for the pie-chart
	 * @return created pie-chart
	 */
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Chart", dataset);
		chart.setTitle("OS usage");
		
		return chart;
	}

}
