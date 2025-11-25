package com.Smart.entites;


import java.util.ArrayList;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
@Table(name="USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private int id;
	
	
   @NotBlank
   @Size(min=2,max=20,message="name must bi 2to20 character")
	private String name;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message="invalid email !!")
	private String email;
    @NotBlank(message="please enter your password !!")
    @Size(min=6,max=20,message="password must bi 6to20 character")
	private String password;
    private String role;
    private boolean enabled;
    private String image;
    @Column(length = 500)
    @NotBlank
    private String about;
    
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "user")
    private List<Contact> contect=new ArrayList<>();
    
    
    
	public List<Contact> getContect() {
		return contect;
	}
	public void setContect(List<Contact> contect) {
		this.contect = contect;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean endable) {
		this.enabled = endable;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String name, String email, String password, String role, boolean enabled, String image,
			String about, List<Contact> contect) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.image = image;
		this.about = about;
		this.contect = contect;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", endable=" + enabled + ", image=" + image + ", about=" + about + ", contect=" + contect + "]";
	}
	
    
}
