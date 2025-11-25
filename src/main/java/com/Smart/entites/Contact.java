package com.Smart.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String cname;
	private String lastname;
	private String cwork;
	private String cemail;
	private String cphone;
	private String cimage;
	@Column(length = 5000)
	private String desription;
	@ManyToOne
	private User user;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCwork() {
		return cwork;
	}
	public void setCwork(String cwork) {
		this.cwork = cwork;
	}
	public String getCemail() {
		return cemail;
	}
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	public String getCimage() {
		return cimage;
	}
	public void setCimage(String cimage) {
		this.cimage = cimage;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contact(int cid, String cname, String lastname, String cwork, String cemail, String cphone, String cimage,
			String desription, User user) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.lastname = lastname;
		this.cwork = cwork;
		this.cemail = cemail;
		this.cphone = cphone;
		this.cimage = cimage;
		this.desription = desription;
		this.user = user;
	}
/*	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", cname=" + cname + ", lastname=" + lastname + ", cwork=" + cwork + ", cemail="
				+ cemail + ", cphone=" + cphone + ", cimage=" + cimage + ", desription=" + desription + ", user=" + user
				+ "]";
	}*/
	
	
	

}
