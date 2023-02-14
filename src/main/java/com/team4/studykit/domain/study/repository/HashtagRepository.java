package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    boolean existsByHashtagName(String hashtagName);

    Hashtag findByHashtagName(String hashtagName);
}
