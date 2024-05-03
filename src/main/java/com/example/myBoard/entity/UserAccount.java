package com.example.myBoard.entity;

import com.example.myBoard.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @Column(name="user_id", length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 100)
    private String email;
    @Column(name = "nickname", length = 50)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    //Oauth2 데이터 저장용
    private String provider;
    private String providerId;
}
