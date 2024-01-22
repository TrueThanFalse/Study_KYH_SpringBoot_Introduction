package hello.hellospring;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {
    // Class를 생성하면 public으로 생성되는데 public을 지워도 문제 없다.
    // 왜? 다른 곳에서 사용할 클래스가 아니기 때문에...

    /*
     class를 Run하면 모든 @Test를 한번에 검사할 수 있다.
     하지만 @Test를 진행하는 것에 순서를 보장하지는 않는다.
     즉, 어떤 @Test에서 spring1을 저장하고 다른 @Test에서 또 spring1을
     저장을 하면 테스트 도중에 Error가 발생한다.
     따라서 항상 @Test가 하나 끝나면 데이터를 클리어 해줘야 한다.
     => 테스트가 끝나면 데이터를 클리어하는 Logic을 만들어야 함
     => @AfterEach : Method의 실행이 끝날 때마다 @AfterEach가 실행됨
     */
    @AfterEach
    public void afterEach() {
        // MemoryMemberRepository 클래스에 clearStore Method를 작성한 후
        // afterEach를 작성해준다.

        repository.clearStore();
    }

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @Test
    // @Test 어노테이션을 적용하면 JUnit 프레임워크로 테스트 할 수 있다.
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        // Optional에서 값을 추출하고 싶을 때는 get() Method를 사용하면 된다.
        // 하지만 get()으로 바로 추출하는 방법은 좋은 방법이 아니라는 것은  알아두자

        System.out.println("result = " + (result == member));
        /*
         console에 출력하여 비교할 수 있지만 항상 출력하는 것은 비효율적이다.
         따라서 Assert 기능을 활용하는 것이 좋은 방법이다.
         */

        // import : org.assertj.core.api.Assertions;
        assertThat(member).isEqualTo(result);

        // 실무에서는 이런 Test들을 Build 툴과 연동하여 Build할 때
        // 자동으로 Test가 진행되고 오류가 발생하면 Build를 막아버림
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
        // 'member1' 넣어서 검사하면 통과
        // 'member2' 넣어서 검사하면 Error
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
