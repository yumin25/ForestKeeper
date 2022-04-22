package com.ssafy.forestkeeper.domain.dao.user;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
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

    @Column(updatable = false, length = 10)
    private String roles; // USER, ADMIN

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

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
