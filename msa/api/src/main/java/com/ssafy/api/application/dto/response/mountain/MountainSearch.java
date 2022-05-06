package com.ssafy.api.application.dto.response.mountain;

import lombok.*;

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
