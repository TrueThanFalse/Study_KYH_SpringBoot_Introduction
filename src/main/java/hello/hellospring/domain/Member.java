package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity // @Entity 어노테이션 : JPA가 관리하는 Entity라는 것을 표현
public class Member {

    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동으로 생성할 경우 IDENTITY
    private Long id;
    // id는 클라이언트가 정하는 값이 아닌 시스템이 정하는 값이다.

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
