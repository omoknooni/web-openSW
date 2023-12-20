package hello.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Recipt {

    @Id
    @GeneratedValue
    @Column(name = "RECIPT_ID")
    private Long id;

    private String filePath;

    private Integer sum;

    private String date;

    private Boolean cash;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /*//==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getRecipts().add(this);
    }*/

}
