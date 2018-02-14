package com.avviotech.labs.patientq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avviotech.labs.patientq.dto.Counter;
import com.avviotech.labs.patientq.repository.CounterRepository;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Override
    public long getNext() {
    	if(counterRepository.findAll().size() == 0)
    	{
    		Counter counter = new Counter("seq", 1);
            counterRepository.save(counter);
            return counter.getSeq();
    	}
    	else
    	{
    		Counter counter = counterRepository.findOne("seq");
            long id = counter.getSeq();
            counter.setSeq(id + 1);
            counterRepository.save(counter);
            
            return counter.getSeq();
    	}
    }
}
