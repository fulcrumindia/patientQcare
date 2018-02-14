package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Provider;

public interface ProviderRepository extends MongoRepository<Provider, Long> {

	@Query("{'_id':?0}")
	Provider findByid(Long id);
}
