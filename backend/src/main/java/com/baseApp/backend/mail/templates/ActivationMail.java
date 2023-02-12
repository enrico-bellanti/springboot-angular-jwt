package com.baseApp.backend.mail.templates;

import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.baseApp.backend.utils.TranslateUtils.tl;

public class ActivationMail extends Mail {

    @Value("${front-end.url.base}")
    String pathBase;

    String token;

    public ActivationMail(User recipient, String token) {
        super();
        setRecipient(recipient);
        this.token = token;
    }

    public Mail build(){
        var recipient = getRecipient();
        Locale locale = recipient.getPreferredLang() != null ? recipient.getPreferredLang() : Locale.getDefault();
        //todo get path base from app properties
        String link = "http://localhost:4200/" + "registration/confirm?token=" + token;

        Map<String, Object> templateProps = new HashMap<>() {{
            put("recipientName", recipient.getFirstName());
            put("senderName", "App Administration");
            put("linkActivation", link);
        }};

        String subject = tl("activation_mail_subject", "mailMessages", locale);

        addResource("attachment.png", "classpath:/mail-logo.png");
        setTo(recipient.getEmail());
        setRecipientName(recipient.getFirstName());
        setSubject(subject);
        setTemplateEngine("activation");
        setTemplateEngineProps(templateProps);
        setLocale(locale);
        return this;
    }
}
