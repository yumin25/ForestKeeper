package com.ssafy.forestkeeper.application.dto.request.plogging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDTO {

    private String x;

    private String y;

    private String _lng;

    private String _lat;

}
