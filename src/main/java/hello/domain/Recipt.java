package hello.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    /*//==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getRecipts().add(this);
    }*/

}
