package com.avviotech.labs.patientq.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.CurrentWaitTime;

public interface WaitTimeRepository extends MongoRepository<CurrentWaitTime, Long> {

	@Query("{'_id':?0}")
	CurrentWaitTime findByid(Long id);
	
	@Query("{'currentdate':'?0','limit' : 1}")
	CurrentWaitTime findAllToday(String today);
	
	@Query("{'currentdate':'?0'}")
	List<CurrentWaitTime> findAllDataToday(String today);
}
