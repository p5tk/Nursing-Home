package com.blissstock.nursinghomesupport.repository;

import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<LoginUser, Long>{
	@Query(nativeQuery = true, value="select * from user_mst where user_name=:userName")
	public LoginUser getUserDetails(@Param("userName")String userName);

	@Query(nativeQuery = true, value="select * from user_mst where own_code_number=:code")
	public LoginUser getUserByOwnCode(@Param("code")String owncode);

	
}
