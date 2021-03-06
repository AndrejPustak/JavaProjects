package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet generates an excel table with voting results.
 * @author Andrej
 *
 */
@WebServlet(name="glasanjeXls", urlPatterns = "/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HSSFWorkbook hwb = createHWB(req);
		
		resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje.xls\"");
		
		
		hwb.write(resp.getOutputStream());
		
		
	}
	
	/**
	 * THis method generates a HSSFWorkbook based on the voting results.
	 * @param req servlet request
	 * @return created HSSFWorkbook
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook createHWB(HttpServletRequest req) {
		
		List<Entry<String, Integer>> entries = (List<Entry<String, Integer>>) req.getSession().getAttribute("entries"); 
		Map<String, String> names = (Map<String, String>) req.getSession().getAttribute("names");
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Glasanje");
		
		int i = 0;
		for(Entry<String, Integer> entry : entries) {
			HSSFRow row = sheet.createRow(i);
			
			row.createCell(0).setCellValue(names.get(entry.getKey()));
			row.createCell(1).setCellValue(entry.getValue());
			i++;
		}
		
		return hwb;
		
	}
}
