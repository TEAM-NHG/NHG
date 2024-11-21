package com.ssafy.aiplanner.domain;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.aiplanner.web.dto.AIPlannerRequest;
import com.ssafy.aiplanner.web.dto.AIPlannerResponse;
import com.ssafy.common.util.PromptTemplateLoader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AIPlannerService {
	
	private final PromptTemplateLoader promptLoader;
	
	private final ChatModel chatModel;
	
	public AIPlannerResponse getEvaluation(AIPlannerRequest request) {
		// 유저 프롬프트 템플릿 로드 및 변수 설정
		String userPromptTemplate = request.getUserPlan();
		PromptTemplate userTemplate = new PromptTemplate(userPromptTemplate);
		userTemplate.add("plan", request.getUserPlan());
		String userCommand = userTemplate.render();
		
		// 시스템 프롬프트 로드
		String systemPromptTemplate = promptLoader.loadSystemPrompt(request.getAiType());
		PromptTemplate systemTemplate = new PromptTemplate(systemPromptTemplate);
		String systemCommand = systemTemplate.render();
		
		// 메시지 생성
		Message userMessage = new UserMessage(userCommand);		
		Message systemMessage = new SystemMessage(systemCommand);
		
		// API 호출
		String response = chatModel.call(userMessage, systemMessage);
		return AIPlannerResponse
				.builder()
				.answer(response)
				.build();
	}

	public AIPlannerResponse getAnswer(AIPlannerRequest request) {
		// TODO Auto-generated method stub
		String userPromptTemplate = request.getUserPlan();
		PromptTemplate userTemplate = new PromptTemplate(userPromptTemplate);
		userTemplate.add("plan", request.getUserPlan());
		String userCommand = userTemplate.render();
		
		
		// 메시지 생성
		Message userMessage = new UserMessage(userCommand);
		
		// API 호출
		String response = chatModel.call(userMessage);
		return AIPlannerResponse
				.builder()
				.answer(response)
				.build();
	}
}
