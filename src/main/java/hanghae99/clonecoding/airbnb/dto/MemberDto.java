package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String picture;
    private String phone;
    private String birth;
    private String intro;
    private String communicate;
    private Boolean isHost;

    public MemberDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.phone = member.getPhone();
        this.birth = member.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.intro = member.getIntro();
        this.communicate = member.getCommunicate();
        this.isHost = member.isHost();
    }
    public Member getMember(BCryptPasswordEncoder encoder){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .birth(LocalDate.parse(this.birth))
                .password(encoder.encode(this.password))
                .picture(this.picture)
                .phone(this.phone)
                .intro(this.intro)
                .communicate(this.communicate)
                .isHost(this.isHost)
                .build();
    }
}
