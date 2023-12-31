package inhatc.spring.shop.service;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Member;
import inhatc.spring.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Member saveMember(Member member) {

        MemberFormDto memberFormDto = modelMapper.map(member, MemberFormDto.class);
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {

        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember.isPresent()) {
            System.out.println("member : " + findMember);
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /**
     * 로그인 시 사용자 정보를 DB에서 조회하여 반환하는 메소드
     * @param email : 로그인 시 입력한 이메일
     * @return : 사용자 정보
     * @throws UsernameNotFoundException : 사용자 정보를 찾을 수 없을 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + email));

        log.info("[로그인 요청 정보] : " + member);

        // 스프링 시큐리티에서 제공하는 User 객체를 만들어 반환
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
