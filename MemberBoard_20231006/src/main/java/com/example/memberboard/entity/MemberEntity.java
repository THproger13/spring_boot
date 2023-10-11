package com.example.memberboard.entity;

import com.example.memberboard.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String memberEmail;

    @Column(length = 50, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 30)
    private String memberMobile;

    @Column(length = 30)
    private String memberBirth;

    // 회원 가입 시간 컬럼
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //회원 정보 수정 시간 컬럼
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Column
    private int profileAttached;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");

        if(memberDTO.getCreatedAt() != null && !memberDTO.getCreatedAt().isEmpty()) {
            try {
                LocalDateTime createdAt = LocalDateTime.parse(memberDTO.getCreatedAt(), formatter);
                memberEntity.setCreatedAt(createdAt);
            } catch (DateTimeParseException e) {
                System.out.println(e.getStackTrace());
            }
        }else {
            memberEntity.setCreatedAt(LocalDateTime.now());
        }
        memberEntity.setProfileAttached(0);

        return memberEntity;
    }

    public static MemberEntity toSaveEntityWithFile(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");

        // createdAt 값의 null 및 유효성 검사
        if (memberDTO.getCreatedAt() != null && !memberDTO.getCreatedAt().isEmpty()) {
            try {
                LocalDateTime createdAt = LocalDateTime.parse(memberDTO.getCreatedAt(), formatter);
                memberEntity.setCreatedAt(createdAt);
            } catch (DateTimeParseException e) {
                // 올바르지 않은 날짜 포맷의 문자열로 인한 예외 처리
                System.out.println(e.getStackTrace());
            }
        } else {
            // createdAt 값이 null 또는 빈 문자열인 경우의 처리
            // 현재 시간을 기본값으로 설정
            memberEntity.setCreatedAt(LocalDateTime.now());
        }

        memberEntity.setProfileAttached(1);
        return memberEntity;
    }
}


