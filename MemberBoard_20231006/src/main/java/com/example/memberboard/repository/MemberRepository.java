package com.example.memberboard.repository;

import com.example.memberboard.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}
