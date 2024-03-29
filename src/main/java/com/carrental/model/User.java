package com.carrental.model;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User{
	
	@Id
    private Long id;
	
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    private String firstName;
    
    private String lastName;
    
    private long seq;

    public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@NotBlank
    @Size(min=3, max = 50)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@NotBlank
    @Size(min=6, max = 100)
    private String password;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles", 
//    	joinColumns = @JoinColumn(name = "user_id"), 
//    	inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
    
    private String role;

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User() {}

    public User(String firstName, String lastName,String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
}