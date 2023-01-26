package com.team4.studykit.domain.member.entity;

import com.team4.studykit.domain.member.model.Social;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private Social social;

    @Builder
    public Member(Long memberId, String id, String password, String nickname,
                  Boolean joinAccepted, Social social) {
        this.memberId = memberId;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.joinAccepted = joinAccepted;
        this.social = social;
    }
}
