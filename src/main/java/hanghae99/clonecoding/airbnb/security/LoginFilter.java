package hanghae99.clonecoding.airbnb.security;

import hanghae99.clonecoding.airbnb.entity.Member;
import hanghae99.clonecoding.airbnb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private MemberRepository memberRepo;
    private BCryptPasswordEncoder encoder;
    private JwtProvider provider;

    protected LoginFilter(String defaultFilterProcessesUrl,JwtProvider provider ,MemberRepository repository, BCryptPasswordEncoder encoder) {
        super(defaultFilterProcessesUrl);
        this.provider = provider;
        this.memberRepo = repository;
        this.encoder = encoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        Member member = memberRepo.findByEmail(email).orElse(null);
        if(member != null && encoder.matches(password,member.getPassword())){
            return provider.getAuthentication(new MemberDetail(member));
        }else{
            throw new UsernameNotFoundException("Email과 비밀번호를 확인해 주세요.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = provider.generateToken(((MemberDetail)authResult.getDetails()).getMember());
        provider.setTokenHeader(token,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Email, 비밀번호를 확인해 주세요");
    }
}
