package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
AOP는 스프링 빈에 직접 등록하여 AOP가 사용 중이라는 것을
개발자에게 인지시키는 것이 좋은 방법이다.
하지만 이번에는 컴포넌트로 등록하겠다.
 */
@Aspect // AOP로 사용할 수 있도록 지정하는 어노테이션
@Component
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))")
    // hellospring 패키지 하위 모든 곳에 전부 적용하라는 의미
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs + "ms");
        }
    }
}
