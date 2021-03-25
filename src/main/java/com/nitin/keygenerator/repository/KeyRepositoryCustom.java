package com.nitin.keygenerator.repository;

import java.util.Set;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.nitin.keygenerator.entity.Key;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
public interface KeyRepositoryCustom {

  /**
   * Insert keys which are not present in DB
   *
   * @param keys
   * @return BulkWriteResult
   */
  BulkWriteResult insertKeys(Set<String> keys);

  /**
   * Get unused key and mark used
   *
   * @return Key
   */
  Key getUnusedKeyAndMarkUsed();

  /**
   * Insert custom key with used true
   *
   * @param customKey
   * @return UpdateResult
   */
  UpdateResult insertCustomKeyWithUsedTrue(String customKey);
}
