package com.avviotech.labs.patientq.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.QueueData;

public interface QueueDataRepository extends MongoRepository<QueueData, Long> {

	@Query("{'_id':?0}")
	QueueData findByid(Long id);
	Collection<QueueData> findByqueueid(Long queueid);
	
	Collection<QueueData> findBypateintid(Long pateintid);
	
	@Query("{'entertime': {$lt:'?0'},'entertime': {$gte: '?1'}}")
	Collection<QueueData> findLast30Days(Date today, Date last);
	
	@Query("{'entertime': {$lt:'?0'},'entertime': {$gte: '?1'},'traige':?2}")
	Collection<QueueData> findLast30DaysVisit(Date today, Date last,String traige);
	
	@Query("{'entertime': {$lt:'?0'},'traige':?1}")
	Collection<QueueData> findTodayByTraige(Date today, String traige);
	
	@Query("{'entertime': {$lt:'?0'},'queueid':?1}")
	Collection<QueueData> findTodayByQueueType(Date today, Long qid);
	
	@Query("{'entertime': {$lt:'?0'}}")
	Collection<QueueData> findAllToday(Date today);
}
