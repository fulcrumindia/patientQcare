package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.PatientReservationForm;

public interface PateintReservationRepository extends MongoRepository<PatientReservationForm, Long> {

	@Query("{'_id':?0}")
	PatientReservationForm findByid(Long id);
}
