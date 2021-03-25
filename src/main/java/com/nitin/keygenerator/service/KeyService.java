package com.nitin.keygenerator.service;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
public interface KeyService {

  /**
   * Generate the specified number of keys to store in key db
   *
   * @param numberOfKeys
   */
  void generateKeys(int numberOfKeys);

  /**
   * Get an unused key and mark it as used in key db
   *
   * @return
   */
  String getUnusedKeyAndMarkUsed();

  /**
   * Check if given custom key can be used as short url key
   *
   * @param customKey
   * @return
   */
  boolean validateCustomKey(String customKey);
}
