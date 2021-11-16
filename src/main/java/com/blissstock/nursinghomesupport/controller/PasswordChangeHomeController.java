package com.blissstock.nursinghomesupport.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;

import com.blissstock.nursinghomesupport.dao.LoginUserDao;
import com.blissstock.nursinghomesupport.dto.ChangePasswordForm;
import com.blissstock.nursinghomesupport.entity.LoginUser;

import antlr.StringUtils;


/**
 * hello画面のコントローラクラス
 * 今回はログイン済みユーザのユーザ名をビューに渡す処理のみを行う
 * @author aoi
 *
 */
@Controller
public class PasswordChangeHomeController {	
	/**
	 * ログイン成功時に呼び出されるメソッド
	 * SecurityContextHolderから認証済みユーザの情報を取得しモデルへ追加する
	 * @param model リクエストスコープ上にオブジェクトを載せるためのmap
	 * @return helloページのViewName
	 */
	
	@Autowired
	private LoginUserDao userDao;
	
	@GetMapping("/PasswordChangeHome")
	private String init(Model model, ChangePasswordForm form) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		model.addAttribute("changePasswordForm", form);
		return "N00003_ChangePassword.html";
		
	}
	
	  @PostMapping("/ReLogin")
	  public String postChangePassword(@ModelAttribute @Validated ChangePasswordForm form,
		 BindingResult bindingResult, Model model) { 
		  
		  if ( bindingResult.hasErrors() ) 
		  {
			  model.addAttribute("changePasswordForm", form);
			  return "N00003_ChangePassword";
		  }		  
			  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			  String userName = auth.getName();
			  model.addAttribute("userName", userName);
		  //newpasswordとconfirmpasswordをチェックする
		  if(!form.getNewPassword().equals(form.getConfirmPassword()))
		  { 
			  model.addAttribute("errMsg", "Two New Passwords does't match");
			  return "/N00003_ChangePassword";
		  }
		  
		  String curpwd = form.getCurrentPassword();
		  LoginUser user = userDao.findUser(userName);
		  if (user == null)
		  { 
			  model.addAttribute("errMsg", "Current Password is incorrect!");
			  return "N00003_ChangePassword";
		  } 
		//currentpassowrdは正しいかをチェックする
		  if(!curpwd.equals(user.getPassword()))
		  {
			  model.addAttribute("errMsg", "Current Password is incorrect!");
			  return "N00003_ChangePassword";
		  }
		  
		  //passwordを更新する
		  user.setPassword(form.getNewPassword()); 
		  user.setNewAccount("false");
		  int result = userDao.updatePassword(user);
		  if(result > 0)
		  { 
			  String successMessage = "ジを「パスワード変更されました。もう一回ログインしてください";
			  model.addAttribute("successMessage",successMessage);
			  return "N00001_Login.html";
		  } 
		  return "Login";
	  
	  }

}
	


