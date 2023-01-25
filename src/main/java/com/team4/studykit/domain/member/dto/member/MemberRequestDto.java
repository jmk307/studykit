package com.team4.studykit.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    @ApiModelProperty(example = "jimin112688")
    @NotBlank(message = "ID는 필수 입력값입니다.")
    @Size(min = 3)
    private String id;

    @ApiModelProperty(example = "rejin0421@")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @ApiModelProperty(example = "강냉이")
    @NotNull
    private String nickname;

    @NotNull
    private Boolean joinAccepted;
}
