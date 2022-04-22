package com.ssafy.forestkeeper.util.converter;

import com.ssafy.forestkeeper.domain.enums.CommunityCode;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CommunityConverter extends AbstractEnumAttributeConverter<CommunityCode> {

    public static final String ENUM_NAME = "카테고리";

    public CommunityConverter() {
        super(CommunityCode.class, false, ENUM_NAME);
    }

}
