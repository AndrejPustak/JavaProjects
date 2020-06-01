package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

@Entity
@Table(name="blog_comments")
public class BlogComment {

	private Long id;
	private BlogEntry blogEntry;
	private String usersEMail;
	private String message;
	private Date postedOn;
	
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean validate(HttpServletRequest req) {
		boolean result = true;
		if(this.message.isEmpty()) {
			req.setAttribute("error.message", "Message can not be empty!");
			result = false;
		}

		if(this.usersEMail.isEmpty()) {
			req.setAttribute("error.email", "Email can not be empty!");
			result = false;
		} else {
			int l = usersEMail.length();
			int p = usersEMail.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				req.setAttribute("error.email", "Email is not of valid format!");
				result = false;
			}
		}
		return result;
	}
}