package com.ssafy.forestkeeper.application.dto.response.mountain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MountainSearch {
    String mountainCode;
    String name;
    String address;

}
