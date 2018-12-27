package com.example.scoutsapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    private ObjectId _id;
    private String memberId;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String phone;

}