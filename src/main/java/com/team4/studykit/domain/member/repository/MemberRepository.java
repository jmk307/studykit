package com.team4.studykit.domain.member.repository;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.model.Social;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);

    Optional<Member> findByIdAndSocial(String id, Social social);

    boolean existsById(String id);

    boolean existsByNickname(String nickname);

    @Query("select m from Member m join fetch m.founders")
    List<Member> findAllJoinFetch();

    @EntityGraph(attributePaths = "founders")
    @Query("select m from Member m")
    List<Member> findAllEntityGraph();

    @Query("select m from Member m where m.memberId in ?1")
    List<Member> findAllByMemberIdIn(List<Long> memberIds);
}
