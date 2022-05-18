package com.ssafy.forestkeeper.application.dto.request.plogging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("ExpRegisterRequestDTO")
@Getter
@ToString
public class ExpRegisterRequestDTO {

    @ApiModelProperty(name = "플로깅 id")
    @NotBlank
    private String ploggingId;

    @ApiModelProperty(name = "경험치")
    @NotBlank
    private long exp;

}
