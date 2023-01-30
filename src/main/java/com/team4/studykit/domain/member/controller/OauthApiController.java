package com.team4.studykit.domain.member.controller;

import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.member.dto.oauth.OauthRequest;
import com.team4.studykit.domain.member.service.OauthService;
import com.team4.studykit.global.config.CommonApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("api/oauth")
@RequiredArgsConstructor
@Api(tags = "소셜 회원가입, 로그인")
public class OauthApiController {
    private final OauthService oauthService;

    @ApiOperation(value = "Oauth 코드를 포함한 로그인 요청")
    @RequestMapping(value = "{provider}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<CommonApiResponse<MemberResponseDto>> oauthLogin(
            @PathVariable String provider,
            @RequestBody OauthRequest oauthRequest) throws URISyntaxException {
        return oauthService.oauthLogin(provider, oauthRequest.getCode());
    }
}
