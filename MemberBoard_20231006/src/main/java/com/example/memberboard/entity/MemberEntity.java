package com.example.memberboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Column
    private int memberProfileAttached;
}
