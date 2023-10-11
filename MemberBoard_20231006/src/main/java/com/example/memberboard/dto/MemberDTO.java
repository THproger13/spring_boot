package com.example.memberboard.dto;

import com.example.memberboard.entity.BoardFileEntity;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.entity.MemberProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberBirth;
    private String createdAt;
    private String updatedAt;

    private MultipartFile memberProfile;
    private int profileAttached;
    private String originalProfileName;
    private String storedProfileName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        // 파일 첨부 여부에 따라 파일이름 가져가기
        if (memberEntity.getProfileAttached() == 1) {
            MemberProfileEntity memberProfileEntity = new MemberProfileEntity();
            memberDTO.setOriginalProfileName(memberProfileEntity.getOriginalProfileName());
            memberDTO.setStoredProfileName(memberProfileEntity.getStoredProfileName());

            memberDTO.setProfileAttached(1);
        } else {
            memberDTO.setProfileAttached(0);
        }
    return memberDTO;
    }


}
