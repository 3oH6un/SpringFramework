package inhatc.spring.shop.controller;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Member;
import inhatc.spring.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/new")
    public String MemberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    };

    @PostMapping("/member/new")
    public String insertMember(MemberFormDto memberFormDto) {
        log.info("=======================================memberFormDto : " + memberFormDto);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member);

        return "redirect:/";
    }
}
