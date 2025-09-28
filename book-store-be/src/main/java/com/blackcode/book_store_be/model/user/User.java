package com.blackcode.book_store_be.model.user;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userFullName;
    private String userName;
    private String userEmail;
    private String userPassword;

    public User(String userFullName, String userName, String userPassword, String userEmail) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }
}
