package com.nitin.keygenerator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nitin.keygenerator.entity.Key;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
@Repository
public interface KeyRepository extends MongoRepository<Key, String>, KeyRepositoryCustom {

  int countByKeyAndUsedTrue(String customKey);
}
