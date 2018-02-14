package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.avviotech.labs.patientq.dto.Counter;


@Repository
public interface CounterRepository extends MongoRepository<Counter, String> {}