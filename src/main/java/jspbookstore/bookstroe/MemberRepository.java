package jspbookstore.bookstroe;

import jspbookstore.bookstroe.domain.TestMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(TestMember member){
        em.persist(member);
        return member.getId();
    }

    public TestMember find(Long id){
        return em.find(TestMember.class, id);
    }
}
