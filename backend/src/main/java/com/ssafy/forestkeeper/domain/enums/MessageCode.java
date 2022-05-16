package com.ssafy.forestkeeper.domain.enums;

import lombok.Getter;

@Getter
public enum MessageCode implements CommonType {

    TALK("M01", "전송"),
    DELETE("M02", "삭제");

    private String code;

    private String description;

    MessageCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
