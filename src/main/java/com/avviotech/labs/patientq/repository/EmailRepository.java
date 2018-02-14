package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.EmailTemplate;

public interface EmailRepository extends MongoRepository<EmailTemplate, Long> {

	@Query("{'_id':?0}")
	EmailTemplate findByid(Long id);
}
