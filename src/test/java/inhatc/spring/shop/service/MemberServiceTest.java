package inhatc.spring.shop.service;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//@Rollback(value = false)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member createMember() {

        MemberFormDto memberFormDto = MemberFormDto.builder()
                .address("unghoe")
                .email("unghoe@ung.hoe")
                .name("unghoe")
                .password("unghoe")
                .build();

        System.out.println("===========> memberFormDto : " + memberFormDto);   // 암호화 전
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        System.out.println("===========> member : " + member);   // 암호화 후
        return member;
    }

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    void saveMemberTest() {

        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        System.out.println("===========> savedMember : " + savedMember);  // db에 저장된 데이터

        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 Test")
    @Transactional
    public void saveDuplicateMemberTest() {

        Member member1 = createMember();
        Member savedMember1 = memberService.saveMember(member1);
        Member member2 = createMember();

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}