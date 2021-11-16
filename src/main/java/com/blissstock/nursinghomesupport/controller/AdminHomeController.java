package com.blissstock.nursinghomesupport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blissstock.nursinghomesupport.dao.LoginUserDao;
import com.blissstock.nursinghomesupport.entity.LoginUser;


@Controller
public class AdminHomeController {
	
	
	/**
	 * ログイン成功時に呼び出されるメソッド
	 * SecurityContextHolderから認証済みユーザの情報を取得しモデルへ追加する
	 * @param model リクエストスコープ上にオブジェクトを載せるためのmap
	 * @return helloページのViewName
	 */
	
	@Autowired
	private LoginUserDao userDao;
	
	@RequestMapping("/AdminHome")
	private String init(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();
		LoginUser user=userDao.findUser(userName);
		model.addAttribute("userName", userName);
		
		if(user.getNewAccount().equals("true"))
		{
			return "redirect:/PasswordChangeHome";
		}else {
			return "N00008_admin.html";
		}
	}
	
}

