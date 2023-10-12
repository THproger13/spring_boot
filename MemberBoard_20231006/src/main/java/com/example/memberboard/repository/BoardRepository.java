package com.example.memberboard.repository;

import com.example.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 제목으로 검색한 결과를 Page 객체로 리턴
    Page<BoardEntity> findByBoardTitleContaining(String q, Pageable pageable);
    // 작성자로 검색한 결과를 Page 객체로 리턴
    Page<BoardEntity> findByBoardWriterContaining(String q, Pageable pageable);
}
