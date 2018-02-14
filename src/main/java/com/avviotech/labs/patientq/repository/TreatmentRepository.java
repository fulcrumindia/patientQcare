package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Treatments;

public interface TreatmentRepository extends MongoRepository<Treatments, Long> {

	@Query("{'_id':?0}")
	Treatments findByid(Long id);
}
