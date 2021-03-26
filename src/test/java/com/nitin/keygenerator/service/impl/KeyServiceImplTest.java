package com.nitin.keygenerator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bson.BsonValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.bulk.BulkWriteUpsert;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.WriteRequest;
import com.nitin.keygenerator.entity.Key;
import com.nitin.keygenerator.repository.KeyRepository;

/**
 * @author nitinmathew - created on 26/03/2021
 **/
@RunWith(SpringJUnit4ClassRunner.class)
public class KeyServiceImplTest {
  private static final String KEY = "a3esyt9";
  private static final Date DATE = new Date();

  @InjectMocks
  private KeyServiceImpl keyService;

  @Mock
  private KeyRepository keyRepository;

  @Captor
  private ArgumentCaptor<Set<String>> setArgumentCaptor;

  private Key key;

  @Before
  public void setUp() {
    key = Key.builder()
        .key(KEY)
        .used(true)
        .createdDate(DATE)
        .usedDate(DATE)
        .build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(keyRepository);
  }

  @Test
  public void generateKeys_whenSuccessTest() {
    List<BulkWriteUpsert> bulkWriteUpserts = Arrays.asList(null, null);
    BulkWriteResult bulkWriteResult =
        BulkWriteResult.acknowledged(WriteRequest.Type.UPDATE, 2, 0, bulkWriteUpserts, Collections.emptyList());
    when(keyRepository.insertKeys(anySet())).thenReturn(bulkWriteResult);
    int numberOfKeysInserted = keyService.generateKeys(2);
    verify(keyRepository).insertKeys(setArgumentCaptor.capture());
    assertEquals(2, setArgumentCaptor.getValue().size());
    assertEquals(2, numberOfKeysInserted);
  }

  @Test
  public void generateKeys_whenKeyAlreadyGeneratedTest() {
    BulkWriteResult bulkWriteResult = BulkWriteResult
        .acknowledged(WriteRequest.Type.UPDATE, 0, 0, Collections.emptyList(), Collections.emptyList());
    when(keyRepository.insertKeys(anySet())).thenReturn(bulkWriteResult);
    int numberOfKeysInserted = keyService.generateKeys(2);
    verify(keyRepository).insertKeys(setArgumentCaptor.capture());
    assertEquals(2, setArgumentCaptor.getValue().size());
    assertEquals(0, numberOfKeysInserted);
  }

  @Test
  public void getUnusedKeyAndMarkUsed_whenSuccessTest() {
    when(keyRepository.getUnusedKeyAndMarkUsed()).thenReturn(key);
    String uniqueKey = keyService.getUnusedKeyAndMarkUsed();
    verify(keyRepository).getUnusedKeyAndMarkUsed();
    assertEquals(KEY, uniqueKey);
  }

  @Test
  public void getUnusedKeyAndMarkUsed_whenUnusedKeyNotPresentTest() {
    when(keyRepository.getUnusedKeyAndMarkUsed()).thenReturn(null);
    String uniqueKey = keyService.getUnusedKeyAndMarkUsed();
    verify(keyRepository).getUnusedKeyAndMarkUsed();
    assertNull(uniqueKey);
  }

  @Test
  public void validateCustomKey_whenSuccessTest() {
    UpdateResult updateResult = UpdateResult.acknowledged(0, 1L, Mockito.mock(BsonValue.class));
    when(keyRepository.insertCustomKeyWithUsedTrue(KEY)).thenReturn(updateResult);
    boolean isValid = keyService.validateCustomKey(KEY);
    verify(keyRepository).insertCustomKeyWithUsedTrue(KEY);
    assertTrue(isValid);
  }

  @Test
  public void validateCustomKey_whenCustomKeyAlreadyTakenTest() {
    UpdateResult updateResult = UpdateResult.acknowledged(1, 0L, null);
    when(keyRepository.insertCustomKeyWithUsedTrue(KEY)).thenReturn(updateResult);
    boolean isValid = keyService.validateCustomKey(KEY);
    verify(keyRepository).insertCustomKeyWithUsedTrue(KEY);
    assertFalse(isValid);
  }
}