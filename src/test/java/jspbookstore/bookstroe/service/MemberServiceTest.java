package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    public void save() {
        Member member = new Member();
        member.setName("한승범");

        Long saveMemberId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveMemberId));
    }

    @Test
    public void validateDuplicate() throws Exception {
        Member member1 = new Member();
        member1.setName("한승범");
        
        Member member2 = new Member();
        member2.setName("한승범");

        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }
    }
}