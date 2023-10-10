package com.example.memberboard.entity;

import com.example.memberboard.entity.BoardFileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "board_table")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column(length = 50, nullable = false)
    private String boardTitle;

    @Column(length = 20, nullable = false)
    private String boardPass;

    @Column(length = 500)
    private String boardContents;

    @Column(updatable = false)
    @UpdateTimestamp
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column
    private int boardHits;

    @Column
    private int fileAttached;

    // 참조관계 정의
    // mappedBy: 자식 엔티티에 정의한 필드 이름
    // cascade, orphanRemoval: 부모 데이터 삭제시 자식 데이터도 삭제
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();



}