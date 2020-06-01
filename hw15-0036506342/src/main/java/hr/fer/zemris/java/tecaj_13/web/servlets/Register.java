package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web servlet that is used for registration. A registration from is show with all the
 * necessary fields. If the user enters the invalid values errors are shown.
 * @author Andrej
 *
 */
@WebServlet(name="register", urlPatterns="/servleti/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nick = req.getParameter("nick");
		String fn = req.getParameter("fn");
		String ln = req.getParameter("ln");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordHash;
		if(password == null || password.equals("")) {
			passwordHash = "";
		} else {
			passwordHash = ServletUtil.getPasswordHash(password);
		}
		
		BlogUser user = new BlogUser();
		user.setFirstName(fn);
		user.setLastName(ln);
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(passwordHash);
		
		if(!user.validate(req)) {
			req.setAttribute("fn", fn);
			req.setAttribute("ln", ln);
			req.setAttribute("email", email);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		
		JPAEMProvider.getEntityManager().persist(user);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}
	
}
