package com.team4.studykit.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String id;

    private String password;

    private String nickname;

    private Boolean joinAccepted;

    private Boolean isSocial;

    @Builder
    public Member(Long memberId, String id, String password, String nickname,
                  Boolean joinAccepted, Boolean isSocial) {
        this.memberId = memberId;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.joinAccepted = joinAccepted;
        this.isSocial = isSocial;
    }
}
