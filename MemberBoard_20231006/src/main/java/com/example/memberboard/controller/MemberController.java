package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            return "redirect:" + redirectURI;
        } else {
            return "/memberPages/login";
        }
    }
    @GetMapping("/mypage")
    public String myPage (HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "/memberPages/main";
    }

    @GetMapping("/profile-image/{filename}")
    public ResponseEntity<UrlResource> getProfileImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get("C:\\springboot_profile\\", filename);
        UrlResource urlResource = new UrlResource(path.toUri());

        if (urlResource.exists() || urlResource.isReadable()) {
            return ResponseEntity.ok().body(urlResource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    @GetMapping("/admin")
    public String admin() {
        return "/memberPages/admin";
    }

    @GetMapping("/logout")
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);
        return "redirect:/member/list";
    }

}
