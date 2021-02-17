package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 등록
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicate(member);
        memberRepository.save(member);
        return member.getId();

    }

    /**
     * 중복성 검사
     */
    private void validateDuplicate(Member member) {
        if (memberRepository.findByName(member.getName()).size() != 0) {
            throw new IllegalStateException("이미 존재하는 이름 입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     */
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
