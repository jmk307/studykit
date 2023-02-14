package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.relation.MemberStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {
}
