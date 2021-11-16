
package com.blissstock.nursinghomesupport.entity;






import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ログインユーザのユーザ名、パスワードを格納するためのEntity
 * @author aoi
 *
 */
@Entity
@Table(name = "user_mst")
public class LoginUser {
	
	@Column(name = "user_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	@NotBlank(message="Please enter username.")
    @Size(max = 20, min = 8, message = "Username`s length must be between 8 and 20.")
	@Column(name = "user_name",unique = true)
	private String userName;
	
	@NotBlank(message="Please enter password.")
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@Length(min = 4, max = 4) 
	@NotBlank(message="Please enter own code.")
	@Column(name = "own_code_number",unique = true)
	private String ownCodeNumber;
	
	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Column(name= "new_account")
	private String newAccount="true";

	//mapping
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="acc")
	@JsonIgnore
	private HelperInfo helper;
	
	 //getters and setters
	public HelperInfo getHelper() {
		return helper;
	}

	public void setHelper(HelperInfo helper) {
		this.helper = helper;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOwnCodeNumber() {
		return ownCodeNumber;
	}

	public void setOwnCodeNumber(String ownCodeNumber) {
		this.ownCodeNumber = ownCodeNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	 public String getNewAccount() {
		return newAccount;
	}

	public void setNewAccount(String newAccount) {
		this.newAccount = newAccount;
	}
	
	//constructors
	public LoginUser() {
		
	}
	
	public LoginUser(String userName, String password, String role, String ownCodeNumber, Date birthDate) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.ownCodeNumber = ownCodeNumber;
		this.birthDate = birthDate;
		;
	}
	
	
}

