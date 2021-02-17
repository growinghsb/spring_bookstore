package jspbookstore.bookstroe;

import jspbookstore.bookstroe.domain.TestMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember () throws Exception {
        TestMember member = new TestMember();
        member.setName("memberA");

        Long saveId = memberRepository.save(member);
        TestMember result = memberRepository.find(saveId);

        Assertions.assertThat(result.getId()).isEqualTo(member.getId());
        Assertions.assertThat(result.getName()).isEqualTo(member.getName());
        Assertions.assertThat(result).isEqualTo(member);
    }
}