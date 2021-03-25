package com.nitin.keygenerator.service.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.nitin.keygenerator.entity.Key;
import com.nitin.keygenerator.repository.KeyRepository;
import com.nitin.keygenerator.service.KeyService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
@Slf4j
@Service
public class KeyServiceImpl implements KeyService {

  @Autowired
  private KeyRepository keyRepository;

  @Override
  public void generateKeys(int numberOfKeys) {
    Set<String> uniqueKeys = new HashSet<>();
    while (uniqueKeys.size() != numberOfKeys) {
      uniqueKeys.add(RandomStringUtils.randomAlphanumeric(7));
    }

    BulkWriteResult bulkWriteResult = keyRepository.insertKeys(uniqueKeys);

    log.info("New keys added: {}", bulkWriteResult.getUpserts().size());
  }

  @Override
  public String getUnusedKeyAndMarkUsed() {
    Key key = keyRepository.getUnusedKeyAndMarkUsed();
    return key.getKey();
  }

  @Override
  public boolean validateCustomKey(String customKey) {
    UpdateResult updateResult = keyRepository.insertCustomKeyWithUsedTrue(customKey);
    return Objects.nonNull(updateResult.getUpsertedId());
  }
}
