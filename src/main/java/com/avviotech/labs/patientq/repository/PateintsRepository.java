package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Pateint;

public interface PateintsRepository extends MongoRepository<Pateint, Long> {

	@Query("{'_id':?0}")
	Pateint findByid(Long id);
	
	Pateint findByemail(String email);
}
