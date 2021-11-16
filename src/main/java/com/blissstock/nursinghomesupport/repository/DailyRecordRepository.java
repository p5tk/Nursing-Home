package com.blissstock.nursinghomesupport.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.DailyRecord;

@Repository
public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long>{
	@Query(value="select * from elders_daily_record where elder_elder_id=:elder_id",nativeQuery=true)
	public List<DailyRecord> findByElderId(@Param("elder_id") Long elder_id);
	
	@Query(value="select * from elders_daily_record where elder_elder_id=:id and date=:record_date",nativeQuery=true)
	public DailyRecord getDailyRecord(@Param("id") Long id,@Param("record_date") Date record_date);

	@Query(value="select * from elders_daily_record where elder_elder_id=:elderId and date>=:start and date<=:end",nativeQuery=true)
	public List<DailyRecord> searchRecordsByDatesAndName( @Param("elderId")Long elderId,@Param("start") Date start,@Param("end") Date end);
	
	@Query(value="select * from elders_daily_record where date>=:start and date<=:end",nativeQuery=true)
	public List<DailyRecord> searchRecordsByDates(@Param("start") Date start,@Param("end") Date end);



	
	

}
