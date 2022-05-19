package com.example.chat.domain.dao.user;

import com.example.chat.domain.dao.BaseEntity;
import com.example.chat.enums.UserCode;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "user_code")
    private UserCode userCode;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_is_deleted")
    private boolean delete;

    // 닉네임 변경
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    // 비밀번호 변경
    public void changePassword(String password) {
        this.password = password;
    }

    // 회원 탈퇴
    public void changeDelete() {
        this.delete = true;
    }

}
