package com.team4.studykit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Api(tags = "EB 헬스체크 컨트롤러(무시해주세용)")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("테스트124");
    }
}
