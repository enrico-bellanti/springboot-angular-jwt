package com.baseApp.backend.models;

import lombok.*;
import org.springframework.core.io.Resource;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Mail {

    @Email
    private String to;

    @Email
    private String from;

    private String recipientName;

    private String subject;

    private String text;

    private String senderName;

    private String templateEngine;

    private Map<String, Object> templateEngineProps = new HashMap<>();

    private Map<String, String> attachments = new HashMap<>();

    private Map<String, String> resources = new HashMap<>();

    private Locale locale;

    public void addTemplateProp(String key, Object value){
        templateEngineProps.put(key, value);
    }

    public void addTemplateProps(Map<String, Object> props){
        templateEngineProps.putAll(props);
    }

    public void addAttachment(String key, String value) {
        attachments.put(key, value);
    }

    public void addAttachments(Map<String, Resource> attachments) {
        attachments.putAll(attachments);
    }

    public void addResource(String key, String value){
        resources.put(key, value);
    }

    public void addResources(Map<String, String> resources){
        resources.putAll(resources);
    }
}
