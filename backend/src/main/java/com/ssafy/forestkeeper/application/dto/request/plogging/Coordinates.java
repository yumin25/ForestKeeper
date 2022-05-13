package com.ssafy.forestkeeper.application.dto.request.plogging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

	private String x;
	private String y;
	private String _lng;
	private String _lat;
}
