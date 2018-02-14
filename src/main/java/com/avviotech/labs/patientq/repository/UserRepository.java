package com.avviotech.labs.patientq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avviotech.labs.patientq.dto.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByuserName(String userName);
    @Query("{'_id':?0}")
    User findByid(String id);
    User findByemailAddress(String email);
    
    @Query("{userName:'?0',password:'?1'}")
    User findByUser(String username,String password);
}
