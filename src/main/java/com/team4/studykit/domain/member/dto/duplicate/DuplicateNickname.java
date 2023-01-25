package com.team4.studykit.domain.member.dto.duplicate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateNickname {
    @ApiModelProperty(example = "강냉이")
    @NotNull
    private String nickname;
}
