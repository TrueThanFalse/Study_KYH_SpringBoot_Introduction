package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // extends JpaRepository를 하면 JpaRepository가 인터페이스에 대한 구현체를 자동으로 만들고
    // 스프링 빈에 등록해준다.

    @Override
    Optional<Member> findByName(String name);
    // findByName 이라고 만들면 스프링 데이터 JPA가
    // 'SELECT m FROM Member m WHERE m.name = ?' 라는 JPQL을 만든다.
    // 즉, 단순한 쿼리문은 인터페이스 이름만으로도 개발을 끝낼 수 있다는 강력한 장점이 있다.
}
