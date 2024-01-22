package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 테스트는 영어권 사람들과 일하는 것이 아니라면
    // 한글로 작성해도 문제가 없다.

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    /*
    테스트가 끝나면 clear를 하고 싶은데 memberService만 있다...
    즉, MemoryMemberRepository를 가져와야 한다.
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    위 구문처럼 MemoryMemberRepository 가져온 후 @afterEach 작성하면 된다.
    하지만 new MemoryMemberRepository()를 하면 실질적으로 MemberRepository 인터페이스에서
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    위 구문으로 생성한 memberRepository와 다른 다른 인스턴스이므로
    뭔가 내용물이 달라지는 등의 문제가 발생할 수 있다.
    따라서 같은 인스턴스를 사용하도록 설정해줘야 한다.

    그렇게 할려면 MemberService 클래스에서 new로 생성하는 것이 아니라
    private final MemberRepository memberRepository; 이렇게 작성한 후
    Shift+N constructor 선택하여 생성자를 만들어서 new로 직접 생성하는 것이 아닌
    생성자로 외부에서 주입하도록 설정해주면 된다.

    그 후에 @BeforeEach 어노테이션을 활용하여 @Test가 동작하기 전에 각각 생성해주면
    동일한 memoryMemberRepository를 사용하도록 설정할 수 있다.
     */
    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    // 테스트 끝날 때마다 클리어
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // 테스트 작성시 팁 : given, when, then 문법
        // 테스트를 한눈에 파악하기 매우 좋다.

        // given
        Member member = new Member();

        // when
        Long saveID = memberService.join(member);
        
        // then
        /*
         Test 클래스를 자동 생성하면 import static org.junit.jupiter.api.Assertions.*;
         Assertions가 자동 import 되는데 우리가 사용할 것은
         import org.assertj.core.api.Assertions이므로 따로 import 해줘야 한다.
         */
        Member findMember = memberService.findOne(saveID).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

        // 여기까지 테스트를 한다면 이 테스트는 반쪽짜리 테스트가 된다. 왜?
        /*
         테스트는 정상 플로우도 중요하지만 사실상 예외 플로우가 훨씬 더 중요하다.
         따라서 회원가입의 핵심은 회원 등록이 되는 것도 중요하지만
         중복 검증 로직이 잘 작동해서 예외 상황이 발생하는 것도 매우 중요하다.
         => 중복_회원_예외() Method를 만들어야 한다.
         */
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        /*
        아래 코드처럼 try~catch 구문으로 Exception 검증을 할 수도 있지만
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        더 가독성이 좋은 문법을 제공하고 있으므로 그것을 활용해 보자
        */

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 만약 IllegalStateException이 아닌 다른 Exception을 넣고(예를 들어 NullPointerException)
        // @Test를 Run하면 실패 한다.
        // 참고 사항 : assertThrows => import static org.junit.jupiter.api.Assertions.*;

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}