package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    @GetMapping("/mypage")
    public String myPage () {
        return "/memberPages/main";
    }
}
