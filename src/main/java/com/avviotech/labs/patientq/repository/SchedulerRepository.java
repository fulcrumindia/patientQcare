package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Scheduler;

public interface SchedulerRepository extends MongoRepository<Scheduler, Long> {

	@Query("{'_id':?0}")
	Scheduler findByid(Long id);
}
