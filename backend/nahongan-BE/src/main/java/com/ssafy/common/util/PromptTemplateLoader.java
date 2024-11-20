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

	@Value("classpath:prompts/happy-system-prompt.st")
    private Resource happyPromptResource;

    @Value("classpath:prompts/angry-system-prompt.st")
    private Resource angryPromptResource;
    
    @Value("classpath:prompts/worry-system-prompt.st")
    private Resource worryPromptResource;
    
    @Value("classpath:prompts/strict-system-prompt.st")
    private Resource strictPromptResource;

    public String loadSystemPrompt(String type) {
    	Resource resource;
    	switch (type) {
    		case "strict": 
    			resource = strictPromptResource; 
    			break;
    		case "worry": 
    			resource = worryPromptResource;
    			break;
    		case "angry": 
    			resource = angryPromptResource;
    			break;
    		case "happy": 
    			resource = happyPromptResource;
    			break;
    		default: 
    			resource = happyPromptResource;
    			break;
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
