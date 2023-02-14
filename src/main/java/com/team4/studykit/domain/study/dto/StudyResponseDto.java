package com.team4.studykit.domain.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team4.studykit.domain.study.entity.Hashtag;
import com.team4.studykit.domain.study.entity.Study;
import com.team4.studykit.domain.study.entity.relation.StudyHashtag;
import com.team4.studykit.domain.study.model.Face;
import com.team4.studykit.domain.study.model.Role;
import com.team4.studykit.domain.study.model.Template;
import com.team4.studykit.global.util.Time;
import lombok.*;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //NULL 필드 가림
public class StudyResponseDto {
    private Long studyId;

    private String title;

    private String description;

    private String deadline;

    private List<String> studyImageUrl;

    private int max;

    private String lang;

    private String tool;

    private Template template;

    private Face face;

    private Set<String> qna = new LinkedHashSet<>();

    private String percent;

    private String founder;

    private List<String> hashtags;

    private Role role;

    @Builder
    public StudyResponseDto(Long studyId, String title, String description,
                            String deadline, List<String> studyImageUrl, int max,
                            String lang, String tool, Template template, Face face,
                            Set<String> qna, String percent, String founder, List<String> hashtags, Role role) {
        this.studyId = studyId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.studyImageUrl = studyImageUrl;
        this.max = max;
        this.lang = lang;
        this.tool = tool;
        this.template = template;
        this.face = face;
        this.qna = qna;
        this.percent = percent;
        this.founder = founder;
        this.hashtags = hashtags;
        this.role = role;
    }

    public static StudyResponseDto of(Study study) {
        return StudyResponseDto.builder()
                .studyId(study.getStudyId())
                .title(study.getTitle())
                .description(study.getDescription())
                .deadline(study.getDeadline())
                .studyImageUrl(study.getStudyImageUrl())
                .max(study.getMax())
                .lang(study.getLang())
                .tool(study.getTool())
                .template(study.getTemplate())
                .face(study.getFace())
                .qna(study.getQna())
                .percent(Time.calculatePercent(study.getDeadline()))
                .founder(study.getFounder().getNickname())
                .hashtags(study.getHashtags().stream()
                        .map(StudyHashtag::getHashtag)
                        .map(Hashtag::getHashtagName)
                        .collect(Collectors.toList()))
                .build();
    }

    public static StudyResponseDto of(Study study, Role role) {
        return StudyResponseDto.builder()
                .studyId(study.getStudyId())
                .title(study.getTitle())
                .description(study.getDescription())
                .deadline(study.getDeadline())
                .studyImageUrl(study.getStudyImageUrl())
                .max(study.getMax())
                .lang(study.getLang())
                .tool(study.getTool())
                .template(study.getTemplate())
                .face(study.getFace())
                .qna(study.getQna())
                .percent(Time.calculatePercent(study.getDeadline()))
                .founder(study.getFounder().getNickname())
                .hashtags(study.getHashtags().stream()
                        .map(StudyHashtag::getHashtag)
                        .map(Hashtag::getHashtagName)
                        .collect(Collectors.toList()))
                .role(role)
                .build();
    }
}
