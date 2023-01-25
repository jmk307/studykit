package com.team4.studykit.domain.member.dto.duplicate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateId {
    @ApiModelProperty(example = "jimin112688")
    @NotBlank(message = "ID는 필수 입력값입니다.")
    @Size(min = 3)
    private String id;
}
