package com.ssafy.aiplanner.web.controller;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.aiplanner.domain.AIPlannerService;
import com.ssafy.aiplanner.web.dto.AIPlannerRequest;
import com.ssafy.aiplanner.web.dto.AIPlannerResponse;
import com.ssafy.common.util.PromptTemplateLoader;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/plan/ai")
public class AIPlannerController {

	private final AIPlannerService aiPlannerService;

	@PostMapping
    public ResponseEntity<AIPlannerResponse> getEvaluation(@RequestBody AIPlannerRequest request) {
		return new ResponseEntity<>(aiPlannerService.getEvaluation(request), HttpStatus.OK);
    }

}
