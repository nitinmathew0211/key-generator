package com.nitin.keygenerator.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.nitin.keygenerator.model.fieldname.KeyFieldNames;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nitinmathew - created on 25/03/2021
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Key.COLLECTION_NAME)
public class Key {
  public static final String COLLECTION_NAME = "key";

  @Id
  @Field(value = KeyFieldNames.ID)
  private String id;

  // Indexed and unique
  @Field(value = KeyFieldNames.KEY)
  private String key;

  // Partially indexed for false value
  @Field(value = KeyFieldNames.USED)
  private boolean used;

  @Field(value = KeyFieldNames.CREATED_DATE)
  private Date createdDate;

  @Field(value = KeyFieldNames.USED_DATE)
  private Date usedDate;
}
