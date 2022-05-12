package com.ssafy.forestkeeper.util.converter;

import com.ssafy.forestkeeper.domain.enums.MessageCode;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MessageConverter extends AbstractEnumAttributeConverter<MessageCode> {

    public static final String ENUM_NAME = "메시지";

    public MessageConverter() {
        super(MessageCode.class, false, ENUM_NAME);
    }

}
