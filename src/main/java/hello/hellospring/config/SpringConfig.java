package hello.hellospring.config;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
@Configuration 어노테이션으로 해당 Class가 환경 설정을 위한
Class임을 인식시켜서 Spring 실행시 즉시 환경 설정을 읽어서
Bean 등록을 하도록 만들 수 있다.

즉, 컴포넌트 스캔 방식을 적용하지 않고 @Configuration 설정으로
Spring에 Bean 등록을 할 수 있다.
 */
@Configuration
public class SpringConfig {

    // @Configuration + @Bean 어노테이션으로
    // Spring이 실행되면 즉시 Bean 등록 가능
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // Controller는 컴포넌트 스캔 방식으로 해야 된다.

}
