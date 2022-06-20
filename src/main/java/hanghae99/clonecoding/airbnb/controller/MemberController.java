package hanghae99.clonecoding.airbnb.controller;

import hanghae99.clonecoding.airbnb.dto.AuthMailDto;
import hanghae99.clonecoding.airbnb.dto.MemberDto;
import hanghae99.clonecoding.airbnb.entity.Member;
import hanghae99.clonecoding.airbnb.security.MemberDetail;
import hanghae99.clonecoding.airbnb.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService service;
    @PostMapping("/member/host")
    public void joinHost(@RequestBody MemberDto memberDto){
        service.getMemberRepo().save(memberDto.getMember(service.getEncoder()));
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
