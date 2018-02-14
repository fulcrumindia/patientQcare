package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Clinic;

public interface ClinicRepository extends MongoRepository<Clinic, Long> {

	@Query("{'_id':?0}")
	Clinic findByid(Long id);
}
