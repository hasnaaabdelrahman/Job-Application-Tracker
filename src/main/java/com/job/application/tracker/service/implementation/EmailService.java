package com.job.application.tracker.service.implementation;

import com.job.application.tracker.exceptions.EmailSendingException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.model.entity.EmailDetails;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.repository.EmailRepository;
import com.job.application.tracker.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;
    private final EmailRepository emailRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public String getRecipient(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id: " + userId)
        );
        return user.getEmail();

    }

    @Async
    public void sendSimpleMessage(String replyToEmail, Integer userId, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setReplyTo(replyToEmail);
        message.setTo(getRecipient(userId));
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Async
    public void sendTemplateEmail(String email ,
                                  String templateName,
                                  Context context) {


        String processHtml = templateEngine.process(templateName, context);


        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Job Application Submitted");
            helper.setText(processHtml, true);

            mailSender.send(message);
        }catch (MessagingException | MailException e) {
            throw new EmailSendingException("Failed to send email");

        }

    }
    public boolean verifyMailExist(String email) {
        return userRepository.existsByEmail(email);
    }

}
