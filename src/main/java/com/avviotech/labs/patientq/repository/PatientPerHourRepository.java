package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.PatientPerHour;

public interface PatientPerHourRepository extends MongoRepository<PatientPerHour, Long> {

	@Query("{'_id':?0}")
	PatientPerHour findByid(Long id);
	
	@Query("{'currentdate':'?0'}")
	PatientPerHour findAllToday(String today);
}
