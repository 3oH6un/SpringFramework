package inhatc.spring.shop.service;

import inhatc.spring.shop.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

public interface MemberService {

    public Member saveMember(Member member);
}
