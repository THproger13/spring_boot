package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.entity.MemberProfileEntity;
import com.example.memberboard.repository.MemberProfileRepository;
import com.example.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        if (memberDTO.getMemberProfile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            return memberRepository.save(memberEntity).getId();
        } else {
            MemberEntity memberEntity = MemberEntity.toSaveEntityWithFile(memberDTO);
            MemberEntity savedEntity = memberRepository.save(memberEntity);
            // 파일 이름 처리, 파일 로컬에 저장 등
            // DTO에 담긴 파일리스트 꺼내기
            MultipartFile memberProfile = memberDTO.getMemberProfile();
                // 업로드한 파일 이름
            String originalProfileName = memberProfile.getOriginalFilename();
                // 저장용 파일 이름
            String storedProfileName = System.currentTimeMillis() + "_" + originalProfileName;
                // 저장경로+파일이름 준비
            String savePath = "C:\\springboot_profile\\" + storedProfileName;
                // 파일 폴더에 저장
            memberProfile.transferTo(new File(savePath));
                // 파일 정보 member_profile_table에 저장
                // 파일 정보 저장을 위한 MemberProfileEntity 생성
            MemberProfileEntity memberProfileEntity =
                    MemberProfileEntity.toSaveProFile(savedEntity, originalProfileName, storedProfileName);
            memberProfileRepository.save(memberProfileEntity);

            return savedEntity.getId();
        }
    }

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if(optionalMemberEntity.isPresent()) {
            return true;
        }else {
            return false;
        }
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        }else {
            return null;
        }
    }
}
