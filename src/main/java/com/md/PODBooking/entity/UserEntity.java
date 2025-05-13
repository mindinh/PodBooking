package com.md.PODBooking.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "users")
@Data
@AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    private String id;

    private String fullName;
    private String userEmail;
    private String userPhone;
    private String password;

    private String userDOB;
    private String userAvatar;

    private long rewardPoint;

    private Status status;
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
