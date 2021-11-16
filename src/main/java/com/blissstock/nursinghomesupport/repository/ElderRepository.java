
package com.blissstock.nursinghomesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;

@Repository
public interface ElderRepository extends JpaRepository<ElderInfo,Long> {

	@Query(nativeQuery = true, value="select * from elder_info")
	public List<ElderInfo> findName();
	
	@Query(nativeQuery = true, value="select * from elder_info where CONCAT (first_name,' ',last_name) LIKE %:elderName%")
	public ElderInfo findByElderName(@Param("elderName")String elderName);
	
}

