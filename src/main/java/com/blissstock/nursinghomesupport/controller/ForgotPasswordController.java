package com.blissstock.nursinghomesupport.controller;



import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.blissstock.nursinghomesupport.dao.LoginUserDao;
import com.blissstock.nursinghomesupport.dto.ForgotPasswordForm;
import com.blissstock.nursinghomesupport.entity.LoginUser;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private LoginUserDao userDao;
	
	@GetMapping("/ResetPassword")
	public String getPasswordResetHome(@ModelAttribute ForgotPasswordForm form, Model model) {
		//Principalからログインユーザの情報を取得
		model.addAttribute("forgotPasswordForm", form);
		return "N00004_ResetPassword";
	}
	
	@PostMapping("/N0004_ResetPassword")
	public String postForgotPassword(@ModelAttribute @Validated ForgotPasswordForm form, BindingResult bindingResult,
	Model model) {
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute("forgotPasswordForm", form);
			return "N00004_ResetPassword.html";
		}
		if(!form.getNewPassword().equals(form.getConfirmPassword())) 
		{
			 model.addAttribute("errMsg", "New Password and Confirm Password does't match");
			return  "/N00004_ResetPassword";
		}
		//own code numberをチェックする
		String owncode=form.getOwnCodeNo();
		System.out.println(owncode);
		LoginUser user;
		user = userDao.findOwnCode(owncode);
		if (user == null)
		{
			 model.addAttribute("errMsg", "User Own Code Not Found");
			return  "/N00004_ResetPassword";
		}
		//date of birthフォーマットをチェックする
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dbDob = sdf.format(user.getBirthDate());
		String inputDob = sdf.format(form.getDateOfBirth());
		if(!dbDob.equals(inputDob)) 
		{
			model.addAttribute("errMsg", "Date Of Birth does't Match");
			return  "/N00004_ResetPassword";
		}
		//パスワードを更新する
		user.setPassword(form.getNewPassword());
		int result = userDao.updateUser(user);
		if(result > 0) 
		{
			return "N00001_Login.html";
		}
		return "N00004_ResetPassword";
		
	}
	


}