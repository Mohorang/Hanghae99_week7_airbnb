package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.AuthMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendHtmlEmail(AuthMailDto dto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setSubject(dto.getTitle());
        helper.setTo(dto.getEmail());
        message.setContent(dto.buildContent(), "text/html; charset=utf-8");
        mailSender.send(message);
    }
}
