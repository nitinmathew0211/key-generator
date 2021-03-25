package com.nitin.keygenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nitin.keygenerator.model.apipath.KeyApiPath;
import com.nitin.keygenerator.service.KeyService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
@Slf4j
@RestController
@RequestMapping(KeyApiPath.BASE_PATH)
public class KeyController {

  @Autowired
  private KeyService keyService;

  @PostMapping(value = KeyApiPath.GENERATE_KEYS, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> generateKeys(@RequestParam("numberOfKeys") int numberOfKeys) {
    log.info("Invoking API to generate {} keys", numberOfKeys);
    keyService.generateKeys(numberOfKeys);
    return ResponseEntity.ok(Boolean.TRUE);
  }

  @PutMapping(value = KeyApiPath.GET_UNUSED_KEY, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getUnusedKeyAndMarkUsed() throws JsonProcessingException {
    log.info("Invoking API to get unused key");
    String key = keyService.getUnusedKeyAndMarkUsed();
    return ResponseEntity.ok(key);
  }

  @PutMapping(value = KeyApiPath.VALIDATE_CUSTOM_KEY, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> validateCustomKey(@RequestParam("customKey") String customKey) {
    log.info("Invoking API to validate custom key {}", customKey);
    boolean isValid = keyService.validateCustomKey(customKey);
    return ResponseEntity.ok(isValid);
  }
}
