package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blissstock.nursinghomesupport.entity.ElderHobbies;
import com.blissstock.nursinghomesupport.entity.ElderInfo;

@Repository
public interface elderHobbyRepository extends JpaRepository<ElderHobbies,Long> {
	@Query(nativeQuery = true, value="delete from elder_hobby where elder_elder_id=:elderId")
	public void deleteByElderId(@Param("elderId")Long elderId);
}
