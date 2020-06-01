package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web servlet that accepts all requests that start with /servleti/author/*
 * When only an author name is given all of the authors entries are displayed. If
 * the author is also the logged in user an option to add a new entry is shown.
 * When authors name and id is given a blog entry of that author with the given id is shown
 * with all the comments.
 * 
 * @author Andrej
 *
 */
@WebServlet(name="author", urlPatterns="/servleti/author/*")
public class Author extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		
		path = path.substring(1);
		String[] pathA = path.split("/");
		req.setAttribute("author", pathA[0]);
		
		if(pathA.length == 1) {
			String nick = pathA[0];
			BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(user);
			
			req.setAttribute("entries", entries);
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
		}
		else {
			if(pathA[1].equals("new") || pathA[1].equals("edit")) {
				if(!pathA[0].equals(String.valueOf(req.getSession().getAttribute("current.user.nick")))) {
					req.setAttribute("error", "You dont have access to this page!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				
				if(pathA[1].equals("edit")) {
					long id;
					
					try {
						id = Long.parseLong(req.getParameter("id"));
					} catch(NumberFormatException e) {
						e.printStackTrace();
						resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
						return;
					}
					
					BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
					
					req.setAttribute("entryID", id);
					req.setAttribute("title", entry.getTitle());
					req.setAttribute("text", entry.getText());
				}
				
				
				req.getRequestDispatcher("/WEB-INF/pages/entryFormular.jsp").forward(req, resp);
				return;
			} else {
				long id;
				
				try {
					id = Long.parseLong(pathA[1]);
				} catch(NumberFormatException e) {
					e.printStackTrace();
					resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
					return;
				}
				
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				
				if(entry == null) {
					req.setAttribute("error", "This entry does not exist!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				
				if(!entry.getCreator().getNick().equals(pathA[0])) {
					req.setAttribute("error", "This entry does not belogn to this author!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				
				req.setAttribute("entry", entry);
				req.setAttribute("comments", entry.getComments());
				req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
				return;
			}
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		
		path = path.substring(1);
		String[] pathA = path.split("/");
		req.setAttribute("author", pathA[0]);
		
		if(pathA.length!=2) {
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
			return;
		}
		
		if(pathA[1].equals("update")) {
			if(!req.getParameter("author").equals(String.valueOf(req.getSession().getAttribute("current.user.nick")))) {
				req.setAttribute("error", "You dont have access to this page!");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
			update(req);
			
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
		}
		else {
			long id;
			
			try {
				id = Long.parseLong(pathA[1]);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
				return;
			}
			
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			if(entry == null) {
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + String.valueOf(req.getParameter("author")));
				return;
			}
			
			BlogComment comment = new BlogComment();
			String email;
			if(req.getSession().getAttribute("current.user.id") != null) {
				email = DAOProvider.getDAO().getBlogUser(String.valueOf(req.getSession().getAttribute("current.user.nick"))).getEmail();
			} else {
				email = req.getParameter("email");
			}
			String message = req.getParameter("message");
			comment.setUsersEMail(email);
			comment.setMessage(message);
			comment.setBlogEntry(entry);
			comment.setPostedOn(new Date());
			
			req.setAttribute("entry", entry);
			
			if(comment.validate(req)) {
				JPAEMProvider.getEntityManager().persist(comment);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + path);
				return;
			}
			
			req.setAttribute("comments", entry.getComments());
			req.setAttribute("error", "Can not post a comment with invalid email");
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			
		}
	}

	/**
	 * THis method is used to update the blog entry. The method is called when adding a new entry
	 * or editing an existing one.
 	 * @param req servlet request.
	 */
	private void update(HttpServletRequest req) {
		
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		Date currentDate = new Date();
		
		if(req.getParameter("entryID") != null && !req.getParameter("entryID").equals("")) {
			long id;
			try {
				id = Long.parseLong(String.valueOf(req.getParameter("entryID")));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				return;
			}
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			
			entry.setTitle(title);
			entry.setText(text);
			entry.setLastModifiedAt(currentDate);
			
			return;
		}
		
		BlogEntry entry = new BlogEntry();
		entry.setTitle(title);
		entry.setText(text);
		
		entry.setCreatedAt(currentDate);
		entry.setLastModifiedAt(currentDate);
		BlogUser creator = DAOProvider.getDAO().getBlogUser(String.valueOf(req.getSession().getAttribute("current.user.nick")));
		entry.setCreator(creator);
		
		JPAEMProvider.getEntityManager().persist(entry);
	}
	
}
