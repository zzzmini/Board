package com.example.myBoard.dto;

import com.example.myBoard.constant.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserCreateForm {
    @Size(min=3, max=15)
    @NotEmpty(message = "사용자 ID는 필수 입니다.")
    private String username;
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String password1;
    @NotEmpty(message = "비밀번호 확인은 필수 입니다.")
    private String password2;
    @NotEmpty(message = "이메일은 필수 입니다.")
    private String email;
    private String nickname;
    private String provider;
    private String providerId;
}
