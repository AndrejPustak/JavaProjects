package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * THis servlet generates calculates the sin and cos of the angles ranging form a to b and saves
 * that data to be used in a jsp script. If a is not given the default is 0, and the default for b
 * is 360.
 * @author Andrej
 *
 */
@WebServlet(name = "trigonometric", urlPatterns={"/trigonometric"})
public class Trigonometric extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a = 0;
		int b = 360;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
		}catch(Exception e) {
		}
		
		try {
			b = Integer.valueOf(req.getParameter("b"));
		}catch(Exception e) {
		}
		
		if (b<a) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(b> a + 720) b = a+720;
		
		List<String> sinValues = new ArrayList<String>();
		List<String> cosValues = new ArrayList<String>();
		
		for(int i = 0; i <= b-a; i++) {
			sinValues.add(String.valueOf(Math.sin(Math.toRadians(a+i))));
			cosValues.add(String.valueOf(Math.cos(Math.toRadians(a+i))));
		}
		
		req.setAttribute("varA", a);
		req.setAttribute("varB", b);
		req.setAttribute("cos", cosValues);
		req.setAttribute("sin", sinValues);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);;
		
	}
}
