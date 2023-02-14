package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.Study;
import com.team4.studykit.domain.study.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByTemplate(Template template);

    List<Study> findByTitleContaining(String title);

    List<Study> findByTemplateAndTitleContaining(Template template, String title);
}
