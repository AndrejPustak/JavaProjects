package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web servlet for showing the main page. The main page consists of login form if the user 
 * is not already logged in, a link to registration from and a list of all the blog users.
 * @author Andrej
 *
 */
@WebServlet(name="main", urlPatterns="/servleti/main")
public class Main extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BlogUser user = null;
		String nick = String.valueOf(req.getParameter("nick"));
		if(nick!=null) {
			user = DAOProvider.getDAO().getBlogUser(nick);
		}
		String password = String.valueOf(req.getParameter("password"));
		
		if(user != null & user.getPasswordHash().equals(ServletUtil.getPasswordHash(password))) {
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			
		}
		else {
			req.getSession().setAttribute("nick", nick);
			req.setAttribute("loginError", "Invalid nick or password");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}
}
