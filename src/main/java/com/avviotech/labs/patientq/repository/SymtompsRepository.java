package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Symtoms;

public interface SymtompsRepository extends MongoRepository<Symtoms, Long> {

	@Query("{'_id':?0}")
	Symtoms findByid(Long id);
}
