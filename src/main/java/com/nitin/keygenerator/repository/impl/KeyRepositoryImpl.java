package com.nitin.keygenerator.repository.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.nitin.keygenerator.entity.Key;
import com.nitin.keygenerator.model.fieldname.KeyFieldNames;
import com.nitin.keygenerator.repository.KeyRepositoryCustom;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
public class KeyRepositoryImpl implements KeyRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public BulkWriteResult insertKeys(Set<String> keys) {
    BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Key.class);

    keys.forEach(key -> {
      Query query = new Query(Criteria.where(KeyFieldNames.KEY).is(key));

      Update update = new Update();
      update.setOnInsert(KeyFieldNames.USED, false);
      update.setOnInsert(KeyFieldNames.CREATED_DATE, new Date());

      bulkOperations.upsert(query, update);
    });

    return bulkOperations.execute();
  }

  @Override
  public Key getUnusedKeyAndMarkUsed() {
    Query query = new Query(Criteria.where(KeyFieldNames.USED).is(false));
    query.limit(1);

    Update update = new Update();
    update.set(KeyFieldNames.USED, true);
    update.set(KeyFieldNames.USED_DATE, new Date());

    return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Key.class);
  }

  @Override
  public UpdateResult insertCustomKeyWithUsedTrue(String customKey) {
    Query query = new Query(Criteria.where(KeyFieldNames.KEY).is(customKey));

    Update update = new Update();
    update.setOnInsert(KeyFieldNames.USED, true);
    Date currentDate = new Date();
    update.setOnInsert(KeyFieldNames.CREATED_DATE, currentDate);
    update.setOnInsert(KeyFieldNames.USED_DATE, currentDate);

    return mongoTemplate.upsert(query, update, Key.class);
  }
}
