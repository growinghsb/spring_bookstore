package jspbookstore.bookstroe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember () throws Exception {
        Member member = new Member();
        member.setName("memberA");

        Long saveId = memberRepository.save(member);
        Member result = memberRepository.find(saveId);

        Assertions.assertThat(result.getId()).isEqualTo(member.getId());
        Assertions.assertThat(result.getName()).isEqualTo(member.getName());
        Assertions.assertThat(result).isEqualTo(member);
    }
}