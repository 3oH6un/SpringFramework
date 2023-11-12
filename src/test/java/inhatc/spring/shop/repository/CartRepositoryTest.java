package inhatc.spring.shop.repository;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Cart;
import inhatc.spring.shop.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;

    public Member createMember(String name) {
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .email(name + "@ung.hoe")
                .name(name)
                .address("인천광역시 서구")
                .password(name + "test")
                .build();

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트 성공")
    public void findCartAndMemberSuccessTest() {
        Member member = createMember("unghoe");
        memberRepository.save(member);

        Cart cart = Cart.builder()
                .member(member)
                .build();
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("member = " + member);
        System.out.println("savedCart = " + savedCart);
        assertEquals(savedCart.getMember().getId(), member.getId());
    }

//    @Test
//    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트 실패")
//    public void findCartAndMemberFailureTest() {
//
//        Member member1 = createMember("unghoe1");
//        memberRepository.save(member1);
//        Member member2 = createMember("unghoe2");
//        memberRepository.save(member2);
//
//        Cart cart = Cart.builder()
//                .member(member1)
//                .build();
//        cartRepository.save(cart);
//
//        em.flush();
//        em.clear();
//
//        Cart savedCart = cartRepository.findById(cart.getId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        System.out.println("member1 = " + member1);
//        System.out.println("member2 = " + member2);
//        System.out.println("savedCart = " + savedCart);
//        // 회원 1의
//        assertEquals(savedCart.getMember().getId(), member2.getId());
//    }
}