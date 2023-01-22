package com.team4.studykit;

import com.team4.studykit.global.config.CommonApiResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "EB 헬스체크 컨트롤러(무시해주세용)")
public class HealthCheckController {
    @GetMapping
    public CommonApiResponse<Boolean> healthCheck() {
        return CommonApiResponse.of(true);
    }
}
