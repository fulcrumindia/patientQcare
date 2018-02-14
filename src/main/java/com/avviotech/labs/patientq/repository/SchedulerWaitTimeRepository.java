package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.SchedulerWaitTime;

public interface SchedulerWaitTimeRepository extends MongoRepository<SchedulerWaitTime, Long> {

	@Query("{'_id':?0}")
	SchedulerWaitTime findByid(Long id);
}
