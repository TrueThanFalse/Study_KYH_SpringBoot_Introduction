package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        // 멤버 서비스 입장에서 보면 직접 new로 생성하지 않고 외부에서
        // MemberRepository를 넣어 주고 있다.
        // 이런 것을 Dependency injection 이라고 한다. (DI)
    }

    // 회원 가입
    public Long join(Member member) {
        // 규칙 : 같은 이름의 중복 회원은 가입 금지

        /*
        Optional<Member> result = memberRepository.findByName(member.getName());

        만약 Optional 객체로 result를 감싸지 않았다면 아래 구문은 if(result != null)처럼
        null에 대한 처리를 해야 하지만 Optional 객체로 감싸면 Optional 객체가 제공하는
        여러가지 Method를 활용할 수 있다.

        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
         */

        /*
        위 코드를 하나의 구문으로 정리하면
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
        이렇게 정리하여 표현할 수 있다. (깔끔하다.)

        또한 위 코드처럼 뭔가 findByName을 해서 로직이 쭉 나오면 그것을 하나의 Method로 추출하는 것이 좋다.
        단축키 : Ctrl+Alt+M
         */

        validateDuplicateMember(member); // 이름 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    // Ctrl+Alt+M 단축키로 추출되어 생성된 Method
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberID) {
        return memberRepository.findById(memberID);
    }
}
