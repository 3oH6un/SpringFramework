package inhatc.spring.shop.service;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member createMember() {

        MemberFormDto memberFormDto = MemberFormDto.builder()
                .name("웅회")
                .email("test@itc.ac.kr")
                .password("201945022")
                .address("인천광역시 미추홀구")
                .build();
        System.out.println("memberFormDto = " + memberFormDto);

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        System.out.println(member);

        return member;
    }

    @Test
    @Transactional
    @DisplayName("회원가입 테스트 I")
    void saveMemberTestI() {

        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getAddress(), savedMember.getAddress());
    }

    @Test
    @Transactional
    @DisplayName("회원가입 테스트 II")
    void saveMemberTestII() {

        Member member1 = createMember();
        Member savedMember1 = memberService.saveMember(member1);
        Member member2 = createMember();
        Throwable e = assertThrows(IllegalStateException.class,
                () -> memberService.saveMember(member2));

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}