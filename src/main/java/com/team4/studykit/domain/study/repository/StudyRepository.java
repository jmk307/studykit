package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
