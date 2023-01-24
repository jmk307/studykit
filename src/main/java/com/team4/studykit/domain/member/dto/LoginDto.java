package com.team4.studykit.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @ApiModelProperty(example = "jimin112688")
    @NotBlank(message = "ID는 필수 입력값입니다.")
    private String id;

    @ApiModelProperty(example = "rejin0421@")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
