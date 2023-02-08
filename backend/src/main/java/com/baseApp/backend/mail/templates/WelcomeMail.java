package com.baseApp.backend.mail.templates;

import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.User;
import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WelcomeMail extends Mail {

    public WelcomeMail(User recipient) {
        super();
        Locale locale = recipient.getPreferredLang() != null ? recipient.getPreferredLang() : Locale.getDefault();

        Map<String, Object> templateProps = new HashMap<>() {{
            put("recipientName", recipient.getFirstName());
            put("senderName", "Default Sender");
        }};

        String subject = tl("welcome_mail_subject", "mailMessages", locale, "value1", "value2" );

        addResource("attachment.png", "classpath:/mail-logo.png");
        setTo(recipient.getEmail());
        setRecipientName(recipient.getFirstName());
        setSubject(subject);
        setTemplateEngine("welcome");
        setTemplateEngineProps(templateProps);
        setLocale(locale);
    }

    public WelcomeMail(User recipient, String senderName) {
        super();
        Locale locale = recipient.getPreferredLang() != null ? recipient.getPreferredLang() : Locale.getDefault();

        Map<String, Object> templateProps = new HashMap<>() {{
            put("recipientName", recipient.getFirstName());
            put("senderName", senderName);
        }};

        String subject = tl("welcome_mail_subject", "mailMessages", locale, "value1", "value2" );

        addResource("attachment.png", "classpath:/mail-logo.png");
        setTo(recipient.getEmail());
        setRecipientName(recipient.getFirstName());
        setSubject(subject);
        setTemplateEngine("welcome");
        setTemplateEngineProps(templateProps);
        setLocale(locale);
    }
}
