package hanghae99.clonecoding.airbnb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private LocalDate birth;

    @Column
    private String picture;

    @Column(columnDefinition = "boolean default false")
    private boolean isHost;

    // 호스트
    @Column
    private String intro;

    @Column
    private String communicate;

    public void updateMember(String name,String picture){
        this.name = name;
        this.picture = picture;
    }

}
