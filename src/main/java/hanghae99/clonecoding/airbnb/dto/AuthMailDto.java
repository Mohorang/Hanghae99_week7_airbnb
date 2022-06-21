package hanghae99.clonecoding.airbnb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMailDto {
    private String email;
    private String title;
    private String confirmChars;

    public String buildContent() {
        StringBuffer br = new StringBuffer();
        br.append("<html><body>");
        br.append("<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
        br.append("<h1>" + title + "</h1><br>");
        br.append("<b>Air Bnb 회원가입 인증 메일입니다.</b><br>");
        br.append("<b>아래에 보이는 문자를 입력해 주세요.</b><br>");
        br.append("<h1>" + confirmChars + "</h1>");
        br.append("</body><html>");
        return br.toString();
    }
}
