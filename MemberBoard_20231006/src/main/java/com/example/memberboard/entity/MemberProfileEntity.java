package com.example.memberboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name = "member_profile_table")
public class MemberProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalProfileName;

    @Column
    private String storedProfileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // DB에 생성될 참조 컬럼의 이름
    private MemberEntity memberEntity; // 부모 엔티티 타입으로 정의
}
