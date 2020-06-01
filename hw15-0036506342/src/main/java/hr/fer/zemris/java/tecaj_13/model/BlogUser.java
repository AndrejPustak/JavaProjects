package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Class that represents one object BlogUser for ORM
 * @author Andrej
 *
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {
	
	/**
	 * User id
	 */
	private Long id;
	
	/**
	 * First name
	 */
	private String firstName;
	
	/**
	 * Last name
	 */
	private String lastName;
	
	/**
	 * Nick
	 */
	private String nick;
	
	/**
	 * Email
	 */
	private String email;
	
	/**
	 * password hash
	 */
	private String passwordHash;
	
	/**
	 * List of user's blog entries
	 */
	private List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
	
	/**
	 * Getter for user id
	 * @return user id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for user id
	 * @param id uesr id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter for first name
	 * @return first name
	 */
	@Column(length=40,nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * SEtter for first name
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * GEtter for last name
	 * @return last name
	 */
	@Column(length=40,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for last name
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for nick
	 * @return nick
	 */
	@Column(length=50,nullable=false,unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Setter for nick
	 * @param nick nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter for email
	 * @return email
	 */
	@Column(length=100,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for email
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for password hash
	 * @return password hash
	 */
	@Column(length=40,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter for password hash
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	/**
	 * This method is used to check if all the values of the variables are valid.
	 * @param req servlet request
	 * @return true if it is valid, false otherwise
	 */
	public boolean validate(HttpServletRequest req) {
		boolean result = true;
		if(this.firstName.isEmpty()) {
			req.setAttribute("error.fn", "First name can not be empty!");
			result = false;
		}
		
		if(this.lastName.isEmpty()) {
			req.setAttribute("error.ln", "Last name can not be empty!");
			result = false;
		}
		
		if(this.nick.isEmpty()) {
			req.setAttribute("error.nick", "Nick can not be empty!");
			result = false;
		} else {
			BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
			if(user!=null) {
				req.setAttribute("error.nick", "Nick already in use!");
				result = false;
			}
		}
		
		if(this.passwordHash.isEmpty()) {
			req.setAttribute("error.pw", "Password name can not be empty!");
			result = false;
		}

		if(this.email.isEmpty()) {
			req.setAttribute("error.email", "Email can not be empty!");
			result = false;
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				req.setAttribute("error.email", "Email is not of valid format!");
				result = false;
			}
		}
		return result;
	}
	
}
