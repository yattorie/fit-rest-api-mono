package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.service.MailService;
import com.orlovandrei.fit_rest.util.LoggerUtil;
import com.orlovandrei.fit_rest.util.Messages;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendRegistrationEmail(String email, String username) {
        try {
            String content = generateTemplate(Messages.EMAIL_REGISTRATION_TEMPLATE.getMessage(), Map.of("username", username));
            sendHtmlEmail(email, Messages.EMAIL_REGISTRATION_SUBJECT.getMessage(), content);
            LoggerUtil.logInfo("Registration email sent to {}", email);
        } catch (IOException | TemplateException | MessagingException | MailException e) {
            LoggerUtil.logError("Failed to send registration email to " + email + ": " + e.getMessage(), e);
        }
    }

    private String generateTemplate(String templateName, Map<String, Object> model)
            throws IOException, TemplateException {
        try (StringWriter writer = new StringWriter()) {
            configuration.getTemplate(templateName).process(model, writer);
            return writer.toString();
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent)
            throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}