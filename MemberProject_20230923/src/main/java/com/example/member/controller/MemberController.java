package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String save() {
        return "/memberPages/memberSave";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "/memberPages/memberLogin";
    }

    @GetMapping("/member/login")
    public String login(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI,
                        Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "/memberPages/memberLogin";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,
                        @RequestParam("redirectURI") String redirectURI) {
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());

//            return "index";
            //사용자가 로그인 성공시 직전에 요청한 페이지로 이동시킴
            //별도로 요청한 페이지가 없다면 정상적으로 mypage로 이동시킴(redirect:/member/mypage)
            return "redirect:" + redirectURI;

        }else {
            return "/memberPages/memberLogin";
        }
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "/memberPages/memberLogin";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginEmail");
        return "redirect:/";
    }

    @GetMapping("/members")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/memberList";
    }

    @GetMapping("/member/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/memberUpdate";
    }
    
    @PostMapping("/member/update") 
    public String update(@ModelAttribute MemberDTO memberDTO) {
        try {
            memberService.update(memberDTO);
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            System.out.println("e = " + e);
        }
        return "redirect:/members";
    }

    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/memberMain";
    }
    @PostMapping("/member/dup-check")   //@RequestBody를 하면 http의 body부분에 담아주기 때문에
                                            // 여기서 GEtMapping을 사용하면 get request에는 바디가 없으므로
                                            // RequestBody를 사용못한다.
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if(result) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/member/axios_detail")
    @ResponseBody
    public MemberDTO axios_detail(@RequestParam Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        if(memberDTO != null) {
            return memberDTO;
        }else{
            return null;
        }
    }
    //axios의 put요청을 사용한 수정처리
    @PutMapping("/member/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO, HttpSession session) {
        memberService.update(memberDTO);
        session.removeAttribute("loginEmail");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
