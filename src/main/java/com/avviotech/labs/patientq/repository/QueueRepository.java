package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.Queue;

public interface QueueRepository extends MongoRepository<Queue, Long> {

	@Query("{'_id':?0}")
    Queue findByid(Long id);
    Queue findBytype(String type);
}
