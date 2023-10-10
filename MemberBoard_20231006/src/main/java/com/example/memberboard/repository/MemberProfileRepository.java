package com.example.memberboard.repository;

import com.example.memberboard.entity.MemberProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
}
