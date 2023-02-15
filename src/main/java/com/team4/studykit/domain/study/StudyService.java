package com.team4.studykit.domain.study;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.repository.MemberRepository;
import com.team4.studykit.domain.study.dto.*;
import com.team4.studykit.domain.study.entity.Hashtag;
import com.team4.studykit.domain.study.entity.Study;
import com.team4.studykit.domain.study.entity.StudyApply;
import com.team4.studykit.domain.study.entity.relation.MemberStudy;
import com.team4.studykit.domain.study.entity.relation.StudyHashtag;
import com.team4.studykit.domain.study.model.Role;
import com.team4.studykit.domain.study.model.Template;
import com.team4.studykit.domain.study.repository.*;
import com.team4.studykit.global.config.s3.AwsS3ServiceImpl;
import com.team4.studykit.global.error.ErrorCode;
import com.team4.studykit.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final AwsS3ServiceImpl awsS3Service;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final StudyHashtagRepository studyHashtagRepository;
    private final StudyApplyRepository studyApplyRepository;
    private final MemberStudyRepository memberStudyRepository;

    // 스터디 전체보기
    @Transactional(readOnly = true)
    public List<StudyResponseDto> showStudies(Template template, String searchKeyword) {
        if (template != null && searchKeyword == null) {
            List<Study> studyList = studyRepository.findByTemplate(template);
            return studyList.stream()
                    .map(StudyResponseDto::of)
                    .collect(Collectors.toList());
        } else if (template == null && searchKeyword != null) {
            List<Study> studyList = studyRepository.findByTitleContaining(searchKeyword);
            return studyList.stream()
                    .map(StudyResponseDto::of)
                    .collect(Collectors.toList());
        } else if (template != null) {
            List<Study> studyList = studyRepository.findByTemplateAndTitleContaining(template, searchKeyword);
            return studyList.stream()
                    .map(StudyResponseDto::of)
                    .collect(Collectors.toList());
        } else {
            List<Study> studyList = studyRepository.findAll();
            return studyList.stream()
                    .map(StudyResponseDto::of)
                    .collect(Collectors.toList());
        }
    }
    
    // 스터디 개설
    @Transactional
    public StudyResponseDto makeStudy(String id, StudyRequestDto studyRequestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = Study.builder()
                .title(studyRequestDto.getTitle())
                .description(studyRequestDto.getDescription())
                .deadline(studyRequestDto.getDeadline())
                .studyImageUrl(new ArrayList<>())
                .max(studyRequestDto.getMax())
                .lang(studyRequestDto.getLang())
                .tool(studyRequestDto.getTool())
                .template(studyRequestDto.getTemplate())
                .face(studyRequestDto.getFace())
                .qna(studyRequestDto.getQna())
                .recruiting(true)
                .founder(member)
                .memberStudies(new ArrayList<>())
                .hashtags(new ArrayList<>())
                .studyBoards(new ArrayList<>())
                .build();
        studyRepository.save(study);

        if (studyRequestDto.getHashtags() != null) {
            for (String hashtag : hastagsToSet(studyRequestDto.getHashtags())) {
                hashtagToEntity(study, hashtag);
            }
        }
        return StudyResponseDto.of(study);
    }

    // 스터디 조회
    @Transactional(readOnly = true)
    public StudyResponseDto showStudy(String id, Long studyId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        Role role = study.getFounder().getNickname().equals(member.getNickname())
                ? Role.STUDY_FOUNDER
                : Role.STUDY_MEMBER;

        return StudyResponseDto.of(study, role);
    }

    // 스터디 사용 언어 편집(스터디장)
    @Transactional
    public StudyResponseDto modStudyLang(Long studyId, StudyLangReqeustDto studyLangReqeustDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        study.putLang(studyLangReqeustDto.getLang());
        studyRepository.save(study);

        return StudyResponseDto.of(study, Role.STUDY_FOUNDER);
    }

    // 스터디 사용 도구 편집(스터디장)
    @Transactional
    public StudyResponseDto modStudyTool(Long studyId, StudyToolRequestDto studyToolRequestDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        study.putTool(studyToolRequestDto.getTool());
        studyRepository.save(study);

        return StudyResponseDto.of(study, Role.STUDY_FOUNDER);
    }

    // 스터디 해시태그 추가(스터디장)
    @Transactional
    public StudyResponseDto modStudyhashtags(Long studyId, HashtagRequestDto hashtagRequestDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        for (String hashtag : hastagsToSet(hashtagRequestDto.getHashtagName())) {
            hashtagToEntity(study, hashtag);
        }

        return StudyResponseDto.of(study, Role.STUDY_FOUNDER);
    }

    // 스터디 해시태그 삭제(스터디장)
    @Transactional
    public void delStudyhashtag(HashtagRequestDto hashtagRequestDto) {
        Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagRequestDto.getHashtagName());
        hashtagRepository.deleteById(hashtag.getHashtagId());
    }

    // 스터디 qna 보기
    @Transactional(readOnly = true)
    public QnaResponseDto showQna(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));
        return QnaResponseDto.of(study);
    }

    // 스터디 지원
    @Transactional
    public String makeStudyApply(String id, Long studyId, QnaRequestDto qnaRequestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        if (!study.isRecruiting()) {
            throw new BadRequestException(ErrorCode.STUDY_NOT_RECRUITING);
        } else if (study.getMemberStudies().size() == study.getMax()) {
            throw new BadRequestException(ErrorCode.STUDY_FULL);
        }

        StudyApply studyApply = StudyApply.builder()
                .answers(qnaRequestDto.getAnswers())
                .applicant(member)
                .study(study)
                .build();
        studyApplyRepository.save(studyApply);

        return study.getTitle() + " 지원이 완료되었습니다.";
    }

    // 스터디 지원 전체보기(스터디장)
    @Transactional(readOnly = true)
    public List<StudyApplyResponseDto> showStudyApplicants(Long studyId) {
        List<StudyApply> studyApplyList = studyApplyRepository.findAllByStudy_StudyId(studyId);

        return studyApplyList.stream()
                .map(StudyApplyResponseDto::of)
                .collect(Collectors.toList());
    }

    // 스터디 지원 승인(스터디장)
    @Transactional
    public String approveStudyApplicant(Long studyId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        MemberStudy memberStudy = MemberStudy.builder()
                .member(member)
                .studyMembers(study)
                .build();
        memberStudyRepository.save(memberStudy);

        studyApplyRepository.deleteByStudy_StudyIdAndApplicant_MemberId(studyId, memberId);

        return member.getNickname() + "님의 " + study.getTitle() + " 지원이 승인되었습니다!";
    }

    // 스터디 지원 거절(스터디장)
    @Transactional
    public void delStudyApplicant(Long studyApplyId) {
        studyApplyRepository.deleteById(studyApplyId);
    }

    // 스터디 지원가능 변경(스터디장)
    @Transactional
    public String modRecruiting(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        if (study.isRecruiting()) {
            study.putRecruiting(false);
            studyRepository.save(study);
        } else {
            study.putRecruiting(true);
            studyRepository.save(study);
        }

        return study.isRecruiting()
                ? "스터디 현재지원 가능!"
                : "스터디 현재지원 불가능 ㅠㅠ";
    }

    // 스터디원 조회
    @Transactional(readOnly = true)
    public StudyMemberResponseDto showStudyMembers(String id, Long studyId) {
        Study study =  studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        boolean isEqual = study.getFounder().getNickname().equals(member.getNickname());

        return StudyMemberResponseDto.of(study, isEqual);
    }

    // 해시태그 스플릿
    public Set<String> hastagsToSet(String hashtags) {
        Pattern myPattern = Pattern.compile("#(\\S+)");
        Matcher mat = myPattern.matcher(hashtags);
        Set<String> hashtagSet = new HashSet<>();

        while (mat.find()) {
            hashtagSet.add(mat.group(1));
        }
        return hashtagSet;
    }

    // 해시태그 영속화
    public void hashtagToEntity(Study study, String hashtag) {
        if (hashtagRepository.existsByHashtagName(hashtag)) {
            Hashtag hashtagEntity = hashtagRepository.findByHashtagName(hashtag);
            StudyHashtag studyHashtag = StudyHashtag.builder()
                    .study(study)
                    .hashtag(hashtagEntity)
                    .build();
            studyHashtagRepository.save(studyHashtag);

            study.putStudyHashtag(studyHashtag);
            studyRepository.save(study);
            hashtagEntity.putStudyHashtag(studyHashtag);
            hashtagRepository.save(hashtagEntity);
        } else {
            Hashtag hashtagEntity = Hashtag.builder()
                    .hashtagName(hashtag)
                    .studyList(new ArrayList<>())
                    .build();
            hashtagRepository.save(hashtagEntity);
            StudyHashtag studyHashtag = StudyHashtag.builder()
                    .study(study)
                    .hashtag(hashtagEntity)
                    .build();
            studyHashtagRepository.save(studyHashtag);

            study.putStudyHashtag(studyHashtag);
            studyRepository.save(study);
            hashtagEntity.putStudyHashtag(studyHashtag);
            hashtagRepository.save(hashtagEntity);
        }
    }
}
