package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.AuthMailDto;
import hanghae99.clonecoding.airbnb.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
@Getter
public class MemberService {
    private final BCryptPasswordEncoder encoder;

    private final EmailService emailService;
    private final MemberRepository memberRepo;
    public String checkEmail(String email) throws MessagingException {
        if(memberRepo.checkEmail(email) != 0)
            throw new IllegalArgumentException("");
        String randomString = RandomString.make(6);
        AuthMailDto mailDto = AuthMailDto.builder()
                .email(email)
                .confirmChars(randomString)
                .title("AirBnb 회원가입 인증코드").build();
        emailService.sendHtmlEmail(mailDto);
        return randomString;
    }
}
