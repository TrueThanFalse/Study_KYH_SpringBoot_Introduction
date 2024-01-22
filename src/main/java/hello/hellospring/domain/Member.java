package hello.hellospring.domain;

public class Member {

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
