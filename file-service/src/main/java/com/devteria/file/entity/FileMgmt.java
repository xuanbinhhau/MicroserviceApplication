package com.devteria.file.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "file_mgmt")
public class FileMgmt {
    @MongoId
    String id;
    String onerId;
    String contentType;
    long size;
    String path;
    String md5Checksum;


}
