package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    /*
     * Optional : java 8의 기능
     * null을 처리하는 방법에서 null을 그대로 반환하는 방법 대신
     * Optional로 감싸서 반환하는 방법을 선호함
     * */
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
