package com.icia.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name = "board_file_table")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // LAZY 로딩 전략을 사용할 경우,
    // 엔터티를 처음으로 조회할 때는 관련된 엔터티의 데이터를 로드하지 않습니다.
    // 대신, 실제로 관련된 엔터티의 데이터에 접근할 때까지 로딩을 지연합니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // DB에 생성될 참조 컬럼의 이름

    // 부모 엔티티 타입으로 정의
    // 해당 변수를 통해 현재 엔터티는 BoardEntity 타입의 객체를 참조할 수 있으며,
    // 이를 통해 관계형 데이터베이스 내의 다대일 관계를 객체 지향적으로 다룰 수 있게 됩니다.

    private BoardEntity boardEntity;


    public static BoardFileEntity toSaveBoardFile(BoardEntity savedEntity, String originalFilename, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFilename);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(savedEntity);
        return boardFileEntity;
    }
}