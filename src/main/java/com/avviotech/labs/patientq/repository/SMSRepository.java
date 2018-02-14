package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.SMSTemplate;

public interface SMSRepository extends MongoRepository<SMSTemplate, Long> {

	@Query("{'_id':?0}")
	SMSTemplate findByid(Long id);
}
