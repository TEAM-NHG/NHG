package com.ssafy.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PromptTemplateLoader {

	@Value("classpath:prompts/annoying-system-prompt.st")
    private Resource annoyingPromptResource;

    @Value("classpath:prompts/anger-system-prompt.st")
    private Resource angerPromptResource;
    
    @Value("classpath:prompts/complain-system-prompt.st")
    private Resource complainPromptResource;
    
    @Value("classpath:prompts/happy-system-prompt.st")
    private Resource happyPromptResource;

    public String loadSystemPrompt(String type) {
    	Resource resource;
    	switch (type) {
    		case "strict": resource = complainPromptResource;
    		case "worry": resource = annoyingPromptResource;
    		case "angry": resource = angerPromptResource;
    		case "happy": resource = happyPromptResource;
    		default: resource = complainPromptResource;
    	}
        try {
            return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error loading system prompt template", e);
            throw new RuntimeException("Failed to load system prompt template", e);
        }
    }
//
//    public String loadUserPrompt() {
//        try {
//            return new String(FileCopyUtils.copyToByteArray(userPromptResource.getInputStream()), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            log.error("Error loading user prompt template", e);
//            throw new RuntimeException("Failed to load user prompt template", e);
//        }
//    }
}
