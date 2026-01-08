package com.interviewmate.resumeservice.util;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TemplateRenderer {

    private final SpringTemplateEngine templateEngine;

    public String renderTemplate(String templateId, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);

        // \\ Map templateId to your Thymeleaf HTML template file
        String templateFile = switch (templateId) {
            case "template1" -> "classic-template.html";
            case "template2" -> "creative-template.html";
            case "template3" -> "minimal-template.html";
            case "template4" -> "modern-template.html";
            default -> "classic-template.html";
        };

        return templateEngine.process(templateFile, context);
    }
}
