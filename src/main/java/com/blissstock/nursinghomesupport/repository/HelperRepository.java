package com.blissstock.nursinghomesupport.repository;

import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.TaskInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;





@Repository
public interface HelperRepository extends JpaRepository<HelperInfo,Long> {
	@Query(nativeQuery = true, value="select * from helper_info where first_name ilike %:term% or last_name ilike %:term%")
	public List<HelperInfo> fetchHelpers(@Param("term")String term);

	@Query(nativeQuery = true, value="select * from helper_info where CONCAT (first_name,' ',last_name) LIKE %:helperName% and helper_id=:helperId")
	public HelperInfo findByNameId(@Param("helperName")String helperName,@Param("helperId")Long helperId);
}