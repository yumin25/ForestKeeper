package com.ssafy.forestkeeper.application.dto.response.mountain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MountainSearchDTO {

    String mountainCode;

    String name;

    String address;

}
