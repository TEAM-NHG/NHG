package com.ssafy.aiplanner.web;
//
//import org.springframework.ai.chat.messages.Message;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.prompt.PromptTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.common.util.PromptTemplateLoader;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//@CrossOrigin("*")
//@RequestMapping("/api/plan/ai")
//public class AIPlannerController {
//
//	private final ChatModel chatModel;
//	private final PromptTemplateLoader promptLoader;
//
//	@GetMapping
//    public ResponseEntity<String> getAttractions(@PathVariable("city") String city, @PathVariable("day") String day) {
//        try {
//            // 유저 프롬프트 템플릿 로드 및 변수 설정
//            String userPromptTemplate = promptLoader.loadUserPrompt();
//            PromptTemplate userTemplate = new PromptTemplate(userPromptTemplate);
//            userTemplate.add("city", city);
//            String userCommand = userTemplate.render();
//
//            // 시스템 프롬프트 로드
//            String systemPromptTemplate = promptLoader.loadSystemPrompt();
//            PromptTemplate systemTemplate = new PromptTemplate(systemPromptTemplate);
//            systemTemplate.add("day", day);
//            String systemCommand = systemTemplate.render();
//
//            // 메시지 생성
//            Message userMessage = new UserMessage(userCommand);
//            Message systemMessage = new SystemMessage(systemCommand);
//
//            // API 호출
//            String response = chatModel.call(userMessage, systemMessage);
//            System.out.println(response);
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error processing request: " + e.getMessage());
//        }
//    }
//
//}
