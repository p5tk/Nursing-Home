package com.blissstock.nursinghomesupport.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blissstock.nursinghomesupport.entity.LoginUser;

/**
 * DBへのアクセスメソッドを呼び出すDao
 * @author aoi
 *
 */
@Repository
public class LoginUserDao {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	JdbcTemplate jdbc;
	
	/**
	 * フォームの入力値から該当するユーザを検索 合致するものが無い場合Nullが返される
	 * @param userName
	 * @return 一致するユーザが存在するとき:UserEntity、存在しないとき:Null
	 */
	public LoginUser findUser(String userName) {
		String query = "";
		query += "SELECT * ";
		query += "FROM user_mst ";
		query += "WHERE user_name = :userName "; //setParameterで引数の値を代入できるようにNamedParameterを利用
		
		//EntityManagerで取得された結果はオブジェクトとなるので、LoginUser型へキャストが必要となる
		return (LoginUser)em.createNativeQuery(query, LoginUser.class).setParameter("userName", userName)
				.getSingleResult();
	}
	//password忘れするとowncodenoでチェックする
	public LoginUser findOwnCode(String ownCodeNo) {
		String query = "";
		query += "SELECT * ";
		query += "FROM user_mst ";
		query += "WHERE own_code_number = :ownCodeNo "; 
		
		//EntityManagerで取得された結果はオブジェクトとなるので、LoginUser型へキャストが必要となる
		try {
			return (LoginUser)em.createNativeQuery(query, LoginUser.class).setParameter("ownCodeNo", ownCodeNo)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	//パスワードを忘れた時新しいパスワードを更新する
	public int updateUser(LoginUser user) throws DataAccessException {
		
		int rowNumber = jdbc.update("UPDATE user_mst"
				+ " SET"
				+ " password = ?,"
				+ " role = ?"
				+ " WHERE own_code_number = ?"//setParameterで引数の値を代入できるようにNamedParameterを利用
				, user.getPassword()
				, user.getRole()
				, user.getOwnCodeNumber());
				
		return rowNumber;
	}
	
	//パスワードを変更する
	public int updatePassword(LoginUser user) throws DataAccessException {
		
		int rowNumber = jdbc.update("UPDATE user_mst"
				+ " SET"
				+ " password = ?"
				+" , new_account = ?"
				+ " WHERE user_name = ?"
				, user.getPassword()
				, user.getNewAccount()
				, user.getUserName()
				);
		return rowNumber;
	}
public int updateNewAccount(LoginUser user) throws DataAccessException {
		
		int rowNumber = jdbc.update("UPDATE user_mst"
				+ " SET"
				+ " new_account = ?"
				+ " WHERE user_name = ?"
				, false
				, user.getUserName()
				);
		return rowNumber;
	}
	
}