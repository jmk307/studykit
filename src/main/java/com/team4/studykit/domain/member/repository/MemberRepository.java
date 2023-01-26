package com.team4.studykit.domain.member.repository;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.model.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);

    Optional<Member> findByIdAndSocial(String id, Social social);

    boolean existsById(String id);

    boolean existsByNickname(String nickname);
}
