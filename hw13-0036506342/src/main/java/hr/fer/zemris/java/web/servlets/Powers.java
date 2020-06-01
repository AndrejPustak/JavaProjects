package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet takes the parameters a, b and n and creates an excel table with 
 * n sheets. On each sheet there are numbers from a to b and their powers to the sheet number.
 * IF the parameters are invalid an appropriate jsp script is called.
 * @author Andrej
 *
 */
@WebServlet(name="powers", urlPatterns = "/powers")
public class Powers extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int a = 0;
		int b = 0;
		int n = 0;
		
		try {
			try {
				a = Integer.valueOf(req.getParameter("a"));
				b = Integer.valueOf(req.getParameter("b"));
				n = Integer.valueOf(req.getParameter("n"));
			} catch(NumberFormatException e) {
				throw new NumberFormatException("Arugments are missing or are invalid");
			}
			
			
			if(a<-100 || a>100 || b<-100 || b>100 || n<1 || n>5) {
				throw new IllegalArgumentException("Arguments out of bounds");
			}
			
			if(b < a) {
				int t = a;
				a = b;
				b = t;
			}
			
			HSSFWorkbook hwb = createHWB(a,b,n);
			
			resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
			resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
			
			
			hwb.write(resp.getOutputStream());
			
		} catch (Exception ex) {
			req.setAttribute("error", ex.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
		}
		
	}
	
	/**
	 * THis method generates a HSSFWorkbook based on the given parameters a, b and n.
	 * @param req servlet request
	 * @return created HSSFWorkbook
	 */
	private HSSFWorkbook createHWB(int a, int b, int n) {
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 1; i<n+1; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);
			
			for(int j = 0; j <= b-a; j++) {
				HSSFRow row = sheet.createRow(j);
				
				row.createCell(0).setCellValue(a+j);
				row.createCell(1).setCellValue(Math.pow(a+j, i));
				
			}
		}
		
		return hwb;
		
	}
}
