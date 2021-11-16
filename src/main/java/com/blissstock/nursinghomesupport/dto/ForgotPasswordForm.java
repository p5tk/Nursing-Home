package com.blissstock.nursinghomesupport.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ForgotPasswordForm {

	@NotBlank(message="Please enter own code.")
	@Length(min = 4, max = 4) 
	@Pattern(regexp="[0-9]*")
	private String ownCodeNo;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@NotBlank(message="Please enter new password.")
	private String newPassword;
	
	@NotBlank(message="Please enter confirm password.")
	private String confirmPassword;

	public String getOwnCodeNo() {
		return ownCodeNo;
	}

	public void setOwnCodeNo(String ownCodeNo) {
		this.ownCodeNo = ownCodeNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
