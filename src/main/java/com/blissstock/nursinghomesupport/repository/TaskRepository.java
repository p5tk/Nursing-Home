package com.blissstock.nursinghomesupport.repository;

import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.TaskInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskInfo, Long>{
	@Query(nativeQuery = true, value="select * from task where helper_id_fkey=:helperId")
	public List<TaskInfo> findTask(@Param("helperId")long helperId);	
	
	@Query(nativeQuery = true, value="select * from task where assigned_date=:assignedDate and helper_id_fkey=:helperId")
	public List<TaskInfo> findDate(@Param("assignedDate")LocalDate assignedDate,@Param("helperId")Long helperId);
}