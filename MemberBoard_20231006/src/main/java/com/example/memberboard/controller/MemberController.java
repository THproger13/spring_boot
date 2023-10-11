package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI,
                        Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "redirectURI") String redirectURI, @ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            if ("admin".equals(memberDTO.getMemberEmail())) {
                return "/memberPages/admin";
            }
            return "redirect:" + redirectURI;
        } else {
            return "/memberPages/login";
        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginEmail");
        return "redirect:/";
    }

    @PostMapping("/dup-check")
    @ResponseBody
    public ResponseEntity<Void> duplicateCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("Received email: " + memberEmail); // 로그 출력
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        if(memberDTO == null) {
            System.out.println("Email is available"); // 로그 출력
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("Email is already in use"); // 로그 출력
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/list";
    }
    @GetMapping("/mypage")
    public String myPage () {
        return "/memberPages/main";
    }
}
