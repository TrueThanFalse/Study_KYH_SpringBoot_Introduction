package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
/*
@SpringBootTest : 애플리케이션을 통합 테스트 할 수 있는 어노테이션
=> 애플리케이션의 전체 context를 전부 로드 한다.
=> 즉, 스프링 컨테이너와 테스트를 함께 실행하는 것이다.
MVC 컨트롤러 또는 JPA 저장소와 관련된 애플리케이션 컨텍스트의 일부만 로드하는
@WebMvcTest과 @DataJpaTest와 같은 Spring의 다른 테스트 접근 방식과 대조된다.
 */
@Transactional
/* @Transactional : 'Test 케이스에 적용'하면 테스트를 실행할 때
트랜잭션을 실행하고 DB에 쿼리문을 전송한 후에 테스트가 끝나면
COMMIT을 하지 않고 항상 ROLLBACK을 실행한다. 따라서
DB에 데이터가 남지 않으므로 Test를 반복할 수 있도록 해주는
아주 필수적인 어노테이션이다. */
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository MemberRepository;
    // MemoryMemberRepository에서 MemberRepository로 수정.
    // 하단의 주석처럼 직접 생성하여 주입하지 않고 DI를 활용하자.
    // 또한 Test는 다른 곳에서 활용할 것이 아니므로 생성자 주입보단
    // @Autowired 어노테이션으로 간단하게 사용하는 것이 효율적이다.
    /*
    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }
     */

    // 하단의 @AfterEach도 필요가 없다.
    // 왜냐하면 @Transactional 어노테이션으로 ROLLBACK 처리됨
    /*
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }
    */

    /*
    회원가입 Test를 실행하면 Error가 발생할 것이다. (이미 존재하는 회원)
    따라서 DB에서 모든 Data를 삭제 후 Test를 진행해야 한다.
    => DELETE FROM member;
    */
    @Test
    void 회원가입() {
        // given
        Member member = new Member();

        // when
        Long saveID = memberService.join(member);
        
        // then

        Member findMember = memberService.findOne(saveID).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}