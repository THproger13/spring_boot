package com.example.member.dto;

import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDTO {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setId(memberEntity.getId());
    memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        return memberDTO;
    }



}
