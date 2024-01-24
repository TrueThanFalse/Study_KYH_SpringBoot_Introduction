package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
< 스프링 빈을 등록하는 2가지 방법 >
    1. 스프링 빈을 등록 하는 방법 중 컴포넌트 스캔 방식(자동 의존관계 설정)
    @Component 어노테이션이 있으면 스프링 빈으로 자동 등록이 된다.
    => @Controller, @Service, @Repository 모두 동일하게 컴포넌트 스캔 방식이다.
    => 스프링이 빌드될 때 컴포넌트와 관련된 어노테이션이 있으면 스프링이
    각각 객체를 하나씩 생성해서 스프링 컨테이너에 딱 등록을 한다.
    => AutoWired는 스프링 컨테이너에 등록된 객체를 연결해주는 다리 역할을 함
    
    컴포넌트 스캔의 범위는 HelloSpringApplication(SpringApplication)이 포함된 패키지와
    하위 패키지만 스캔의 대상이 됨

    참고 : Spring은 스프링 빈을 등록할 때, 기본적으로 싱글톤으로 등록한다.
    (설정으로 싱글톤이 아닌 것으로 만들 수 있지만 특별한 경우를 제외하면 싱글톤으로 사용함)
    => 유일하게 하나만 등록하여 모든 곳에서 공유를 한다.

    2. 자바 코드로 직접 스프링 빈 등록
 */
@Controller
public class MemberController {
    /*
     @Controller 어노테이션을 적용하면
     스프링이 실행될 때 스프링 컨테이너가 만들어지는데
     @Controller 어노테이션이 있으면 이 MemberController를
     스프링 컨테이너에 등록한다.
     그리고 Spring에서 스프링 컨테이너를 관리를 한다.
     => 스프링 컨테이너에서 스프링 빈이 관리 된다.
     */

    private final MemberService memberService;

    /*
    스프링이 실행되면서 스프링 컨테이너가 생성될 때 하단의 생성자가 호출된다.
    그리고 생성자에 @Autowired 어노테이션을 적용하면 매개변수에 있는 MemberService를
    Spring이 스프링 컨테이너에 있는 MemberService를 가져와서 주입시킨다.
    => Dependency Injection (DI) : 의존관계 주입
     */
    // ● 생성자 주입 방법
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    /*
    DI는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다.
    의존 관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.

    ● 필드 주입 방법
    @Autowired private MemberService memberService;

    ● setter 주입 방법

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    단점 : MemberController를 호출했을 때 public으로 열려 있어야 함
    => 항상 외부에 Method가 노출 되어 있는 단점이 존재함
     */

    /*
    참고 : 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는
    컴포넌트 스캔을 사용한다. 그리고 정형화 되지 않거나,
    상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
     */
}
