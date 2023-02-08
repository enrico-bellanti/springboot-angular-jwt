package com.baseApp.backend.services;

import com.baseApp.backend.models.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {

    @Value("${mail.config.default-mail-from}")
    private String NO_REPLY_ADDRESS;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SimpleMailMessage simpleTemplate;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    public void setNoReplayAddress(String address){
        this.NO_REPLY_ADDRESS = address;
    }

    public void send(Mail mail){
        if (mail.getTemplateEngine() != null){
            sendMailWithTemplate(mail);
        } else {
            sendSimpleMail(mail);
        }
    }

    private void sendSimpleMessage(String to, String from, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    private void sendSimpleMessageWithAttachments(String to,
                                                  String from,
                                                  String subject,
                                                  String text,
                                                  Map<String, String> attachments) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            attachments.forEach((name, path) -> {
                        FileSystemResource file = new FileSystemResource(new File(path));
                        try {
                            helper.addAttachment(name, file);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateHTMLTemplate(String templateName, Map<String, Object> templateProps, Locale locale){
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateProps);
        thymeleafContext.setLocale(locale);
        return thymeleafTemplateEngine.process(templateName +".html", thymeleafContext);
    }

    private void sendHTMLMail(String to,
                              String from,
                              String subject,
                              String htmlBody,
                              Map<String, String> attachments,
                              Map<String, String> resources) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            if (attachments != null || !attachments.isEmpty()){
                attachments.forEach((name, path) -> {
                            FileSystemResource file = new FileSystemResource(new File(path));

                            try {
                                helper.addAttachment(name, file);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            }

            if (attachments != null || !resources.isEmpty()){
                resources.forEach((name, resourcePath) -> {
                            try {
                                ResourceLoader resourceLoader = new DefaultResourceLoader();
                                Resource resource = resourceLoader.getResource(resourcePath);
                                helper.addInline(name, resource);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            }

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendSimpleMail(Mail mail){
        String from = mail.getFrom() != null ? mail.getFrom() : NO_REPLY_ADDRESS;

        if (mail.getAttachments() == null || mail.getAttachments().isEmpty()){
            sendSimpleMessage(
                    mail.getTo(),
                    from,
                    mail.getSubject(),
                    mail.getText()
            );
        } else {
            sendSimpleMessageWithAttachments(
                    mail.getTo(),
                    from,
                    mail.getSubject(),
                    mail.getText(),
                    mail.getAttachments()
            );
        }
    }

    private void sendMailWithTemplate(Mail mail){
        Locale locale = mail.getLocale() != null ? mail.getLocale() : Locale.getDefault();

        String from = mail.getFrom() != null ? mail.getFrom() : NO_REPLY_ADDRESS;

        String bodyHTML = generateHTMLTemplate(
                mail.getTemplateEngine(),
                mail.getTemplateEngineProps(),
                locale
        );

        sendHTMLMail(
                mail.getTo(),
                from,
                mail.getSubject(),
                bodyHTML,
                mail.getAttachments(),
                mail.getResources()
        );
    }
}
