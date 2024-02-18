package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
// 데이터를 저장하고 변경할 때는 항상 트랜잭션을 적용해야 함
// => JPA를 사용하려면 트랜잭션 어노테이션이 필수적이다.
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        // JPA 라이브러리를 적용하면 스프링부트가 자동으로 EntityManager 객체를 생성함
        // 따라서 우리는 스프링부트에서 인젝션 받으면 됨
        // 즉, JPA를 사용하려면 EntityManager를 주입받아야 된다.
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                // PK 기반이 아닐 경우에는 jpql을 작성해야 한다.
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        // 테이블을 대상으로 쿼리를 전송하는 것이 아니라 객체를 대상으로 쿼리를 전송하는 방법
        // 맵핑이 이미 되어있으므로 JPA가 자동으로 추적하여 쿼리문을 전송한다.
    }
}
