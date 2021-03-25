package com.nitin.keygenerator.model.apipath;

/**
 * @author nitinmathew - created on 26/03/2021
 **/
public interface KeyApiPath {

  String BASE_PATH = "/api/key";
  String GENERATE_KEYS = "/generate-keys";
  String GET_UNUSED_KEY = "/get-unused-key";
  String VALIDATE_CUSTOM_KEY = "/validate-custom-key";
}
