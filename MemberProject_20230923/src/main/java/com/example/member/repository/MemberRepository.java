package com.example.member.repository;

import com.example.member.entity.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//인터페이스는 추상메서드만 정의 가능(중괄호가 없다. )
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//추상메서드(abstract method)
    //select * from member_table where member_email=?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    //추상메서드(abstract method)
    //select * from member_table where member_email=? and member_password= ?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}