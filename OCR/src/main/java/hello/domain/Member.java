package hello.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @NotBlank
//    @Size(min = 5,max = 30)
    private String loginId;

    @NotBlank
//    @Size(min = 5,max = 30)
    private String password;

    @NotBlank
//    @Size(min = 3, max = 20)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Recipt> recipts = new ArrayList<>();

}
