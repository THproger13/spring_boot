package com.icia.board.repository;

import com.icia.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /*
           update board_table set board_hits=board_hits+1 where id=?
           jpql(java persistence query language)
        */
    @Modifying // insert, update, delete
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
//    @Query(value = "update board_table set board_hits=board_hits+1 where id=:id", nativeQuery = true)
    void increaseHits(@Param("id") Long id);
}
