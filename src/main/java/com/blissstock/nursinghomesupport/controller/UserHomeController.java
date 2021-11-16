package com.blissstock.nursinghomesupport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blissstock.nursinghomesupport.dao.LoginUserDao;
import com.blissstock.nursinghomesupport.entity.LoginUser;


/**
 * hello画面のコントローラクラス
 * 今回はログイン済みユーザのユーザ名をビューに渡す処理のみを行う
 * @author aoi
 *
 */
@Controller
public class UserHomeController {
	
	
	/**
	 * ログイン成功時に呼び出されるメソッド
	 * SecurityContextHolderから認証済みユーザの情報を取得しモデルへ追加する
	 * @param model リクエストスコープ上にオブジェクトを載せるためのmap
	 * @return helloページのViewName
	 */
	
	@Autowired
	private LoginUserDao userDao;
	
	@RequestMapping("/UserHome")
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();
		LoginUser user=userDao.findUser(userName);
		model.addAttribute("userName", userName);
		if(user.getNewAccount().equals("true"))
		{
			return "redirect:/passwordChangeHome";
		}else {
			return "N00002_helper.html";
		}
		
	}
}