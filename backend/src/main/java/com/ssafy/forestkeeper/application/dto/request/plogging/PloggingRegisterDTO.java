package com.ssafy.forestkeeper.application.dto.request.plogging;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("PloggingRegisterDTO")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PloggingRegisterDTO {

    @ApiModelProperty(name = "플로깅 시작 시간")
    @NotBlank
    private String startTime;

    @ApiModelProperty(name = "플로깅 종료 시간")
    @NotBlank
    private String endTime;

    @ApiModelProperty(name = "플로깅 거리")
    @NotBlank
    private double distance;
    
    @ApiModelProperty(name = "산 이름")
    @NotBlank
    private String mountainName;
    
    @ApiModelProperty(name = "플로깅 경로 이미지")
    @Nullable
    private MultipartFile image;
}
