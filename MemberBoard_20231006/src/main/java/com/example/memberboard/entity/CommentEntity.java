package com.example.memberboard.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name = "comment_table")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 200, nullable = false)
    private String commentContents;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

//    public static CommentEntity toSaveEntity(BoardEntity boardEntity, CommentDTO commentDTO) {
//        CommentEntity commentEntity = new CommentEntity();
//        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
//        commentEntity.setCommentContents(commentDTO.getCommentContents());
//        commentEntity.setBoardEntity(boardEntity);
//        return commentEntity;
//    }
}