package com.ssafy.forestkeeper.domain.enums;

import lombok.Getter;

@Getter
public enum CommunityCode implements CommonType {

    REVIEW("C01", "등산 후기"),
    QNA("C02", "질의응답");

    private String code;

    private String description;

    CommunityCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
