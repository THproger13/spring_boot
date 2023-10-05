package com.icia.board.dto;

import com.icia.board.entity.BoardEntity;
import com.icia.board.util.UtilClass;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder //빌더란
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private String createdAt;
    private int boardHits;


    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
//        boardDTO.setCreatedAt(boardEntity.getCreatedAt());

        //1. 추가적인 클래스 UtilClass를 만들어서 createdAt세팅하기
        boardDTO.setCreatedAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()));
        //2. 클래스를 따로 만들지 않고 날짜 형식을 갖추도록 creadtedAt 값세팅
//        String formattedDate = boardEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        boardDTO.setCreatedAt(formattedDate);
//
            //빌더를 사용해서 엔티티를 DTO로 변환
//        BoardDTO boardDTO = BoardDTO.builder();
//                .id(boardEntity.getId())
//                .boardWriter(boardEntity.getBoardWriter())
//                .boardTitle(boardEntity.getBoardTitle())
//                .boardPass(boardEntity.getBoardPass())
//                .boardContents(boardEntity.getBoardContents())
//                .boardHits(boardEntity.getBoardHits())
//                .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
//                .build();

        return boardDTO;
    }
}
