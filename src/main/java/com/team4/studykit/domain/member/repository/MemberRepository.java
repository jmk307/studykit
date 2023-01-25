package com.team4.studykit.domain.member;

import com.team4.studykit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);

    boolean existsById(String id);

    boolean existsByNickname(String nickname);
}
