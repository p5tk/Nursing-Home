package com.blissstock.nursinghomesupport.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangePasswordForm {
	
	@NotBlank(message="Please enter current password.")
	private String currentPassword;
	
	@NotBlank(message="Please enter new password.")
	private String newPassword;
	
	@NotBlank(message="Please enter confirm password.")
	private String confirmPassword;
	
	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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
