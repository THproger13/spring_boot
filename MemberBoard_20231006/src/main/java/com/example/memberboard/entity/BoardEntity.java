package com.example.memberboard.entity;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.BoardFileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

    //BoardEntity는 MemberEntity를 참조하는 자식 엔티티이므로 @ManyToOne을 사용한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    public static BoardEntity toSaveEntity(MemberEntity memberEntity, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");

        if(boardDTO.getCreatedAt() != null && !boardDTO.getCreatedAt().isEmpty()) {
            try {
                LocalDateTime createdAt = LocalDateTime.parse(boardDTO.getCreatedAt(), formatter);
                boardEntity.setCreatedAt(createdAt);
            } catch (DateTimeParseException e) {
                System.out.println(e.getStackTrace());
            }
        }else {
            boardEntity.setCreatedAt(LocalDateTime.now());
        }
        boardEntity.setFileAttached(0);

        return boardEntity;
    }

    public static BoardEntity toSaveEntityWithFile(MemberEntity memberEntity, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");

        // createdAt 값의 null 및 유효성 검사
        if (boardDTO.getCreatedAt() != null && !boardDTO.getCreatedAt().isEmpty()) {
            try {
                LocalDateTime createdAt = LocalDateTime.parse(boardDTO.getCreatedAt(), formatter);
                boardEntity.setCreatedAt(createdAt);
            } catch (DateTimeParseException e) {
                // 올바르지 않은 날짜 포맷의 문자열로 인한 예외 처리
                System.out.println(e.getStackTrace());
            }
        } else {
            // createdAt 값이 null 또는 빈 문자열인 경우의 처리
            // 현재 시간을 기본값으로 설정
            boardEntity.setCreatedAt(LocalDateTime.now());
        }
        boardEntity.setFileAttached(1);
        return boardEntity;
    }


}