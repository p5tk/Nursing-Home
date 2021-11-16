package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.HelperShiftDays;



@Repository
public interface HelperShiftDaysRepository extends JpaRepository<HelperShiftDays, Long>{
	
	

}