package hello.hellospring.config;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
    // 참고 : Controller는 컴포넌트 스캔 방식으로 해야 된다.

    @Autowired DataSource dataSource;

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();

        // return new JdbcMemberRepository(dataSource);
        // application.properties에서 DB 연결 설정을 하면
        // Spring이 dataSource 객체를 알아서 생성해 준다. 그것을 가져와서
        // JdbcMemberRepository로 변경하면 아주 손쉽게 DB를 교체할 수 있다.
        // => Spring 다형성의 장점 (중요)

        // ※ 스프링 JdbcTemplate
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
