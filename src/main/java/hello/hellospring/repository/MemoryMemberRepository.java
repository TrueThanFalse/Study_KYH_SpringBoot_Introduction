package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    /*
     실무에서는 동시성 문제가 있을 수 있어서 공유되는 변수일 때는
     concurrentHashMap을 사용해야 하지만 지금은 단순한 예제이므로
     HashMap으로 진행함
     */

    private static long sequence = 0L;
    /*
    sequence : 0,1,2 ... 키 값을 생성하는 변수
    실무에서는 이렇게 long에서 하는 것보다 동시성 문제를 고려해서
    AtomicLong을 사용해야 하는데 지금은 가장 단순하게 진행함
     */

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        // 회원의 id는 1부터 순차적으로 저장
        // => 시스템이 자동으로 증가 시킴
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null이 반환될 가능성이 있다면 Optional로 감싸준다.
        return Optional.ofNullable(store.get(id));
        // ofNullable : store.get(id)의 값이 null이어도 감쌀 수 있다.
        // 이렇게 Optional로 감싸서 반환하면 클라이언트에서 후속 조치를 할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // findAny() : 무언가 찾으면 그것을 반환.
        // 루프를 마지막까지 돌았는데 못 찾으면 Optional에 null이 포함되어 반환됨
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // Test 클리어를 위해 만든 Method
    public void clearStore() {
        store.clear();
    }
}
