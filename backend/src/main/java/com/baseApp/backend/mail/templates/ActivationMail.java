package com.baseApp.backend.mail.templates;

import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.User;
import com.baseApp.backend.utils.AppUtils;
import com.baseApp.backend.utils.AppUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.baseApp.backend.utils.TranslateUtils.tl;

public class ActivationMail extends Mail {

    private String frontEndPathBase;

    private String token;

    public ActivationMail(User recipient, String token) {
        super();
        this.frontEndPathBase = AppUtils.loadAppProperties().getProperty("front-end.url.base");
        setRecipient(recipient);
        this.token = token;

        Locale locale = recipient.getPreferredLang() != null ? recipient.getPreferredLang() : Locale.getDefault();
        setLocale(locale);

        String link = frontEndPathBase + "registration/confirm?token=" + token;

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

    }
}
