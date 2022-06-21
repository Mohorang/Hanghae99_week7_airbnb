package hanghae99.clonecoding.airbnb.controller;

import hanghae99.clonecoding.airbnb.dto.AuthMailDto;
import hanghae99.clonecoding.airbnb.dto.LoginDto;
import hanghae99.clonecoding.airbnb.dto.MemberDto;
import hanghae99.clonecoding.airbnb.entity.Member;
import hanghae99.clonecoding.airbnb.security.JwtProvider;
import hanghae99.clonecoding.airbnb.security.MemberDetail;
import hanghae99.clonecoding.airbnb.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService service;

    private final BCryptPasswordEncoder encoder;

    private final JwtProvider provider;
    @PostMapping("/member/host")
    public void joinHost(@RequestBody MemberDto memberDto){
        service.getMemberRepo().save(memberDto.getMember(service.getEncoder()));
    }

    @PostMapping("/member/login")
    public void login(HttpServletResponse response, @RequestBody LoginDto dto){
        Member member = service.getMemberRepo().findByEmail(dto.getUsername());
        if(member != null && encoder.matches(dto.getPassword(),member.getPassword())){
            String token = provider.generateToken(member);
            provider.setTokenHeader(token,response);
        }else{
            throw new UsernameNotFoundException("Email과 비밀번호를 확인해 주세요.");
        }
    }
    @PostMapping("/member/guest")
    public void joinGuest(@RequestBody MemberDto memberDto){
        service.getMemberRepo().save(memberDto.getMember(service.getEncoder()));
    }

    @PostMapping("/member/email")
    public String confirmEmail(@RequestBody AuthMailDto dto) throws MessagingException {
        return service.checkEmail(dto.getEmail());
    }

    @GetMapping("/member")
    public MemberDto getMemberInfo(@AuthenticationPrincipal MemberDetail detail){
        Member member = detail.getMember();
        return MemberDto.builder()
                .name(member.getName())
                .picture(member.getPicture())
                .build();
    }
}
