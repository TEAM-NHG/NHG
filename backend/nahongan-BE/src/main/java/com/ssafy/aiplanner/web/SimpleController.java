//package com.ssafy.aiplanner.web.controller;
//
//import org.springframework.ai.chat.messages.Message;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.prompt.PromptTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class SimpleController {
//
//	private final ChatModel chatModel;
//
//
//	@GetMapping("/simple/{city}")
//	public String getMethodName(@PathVariable String city) {
//		System.out.println("city : " + city);
//		String command = """
//				<instruction>
//				{city}의 대표적인 관광지에대해 알려줘.
//				1. 관광지 : name,
//				2. 관광타입 : contentTypeId로 하고 값의 경우 (12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) 예시의 숫자로 해줘
//				3. 관련 이미지를 관광공사 홈페이지에서 검색하여 대표 이미지의 실제 url도 넣어 주면 좋을거 같아 : imageUrl.
//				4. 주소 : address
//				5. 관광지 위치의 위도 : latitude, 경도 : longitude도 같이 부탁해.
//				</instruction>
//				<example>
//				\\{
//                    "attractions": [
//                        \\{
//                            "name": "성산 일출봉",
//                            "contentTypeId": 12,
//                            "imageUrl": "http://www.imageex.com/1234",
//                            "address": "제주도 서귀포시 성산동 123",
//                            "latitude": 37.1234567,
//                            "longitude": 101.126673
//                        \\},
//                        \\{
//                            "name": "한강 불꽃 축제",
//                            "contentTypeId": 15,
//                            "imageUrl": "http://www.fet.com/1234",
//                            "address": "서울특별시 용산구 한강동 11",
//                            "latitude": 37.12434567,
//                            "longitude": 101.153473
//                        \\},
//                        \\{
//                            "name": "조선 호텔",
//                            "contentTypeId": 32,
//                            "imageUrl": "http://www.chosun.com/1234",
//                            "address": "부산시 해운대구 해운대동 조선호텔",
//                            "latitude": 37.121217,
//                            "longitude": 101.113232
//                        \\}
//                    ]
//                \\}
//				</example>
//				""";
//		PromptTemplate template = new PromptTemplate(command);
//		template.add("city", city);
////		template.add("content", "life");
//		// Prompt prompt = template.create();
//		String message = template.render();
//		Message userMessage = new UserMessage(message);
//		Message systemMessage = new SystemMessage("""
//				당신은 여행플래너이자 자연 풍경을 전문으로 찍는 포토그래퍼입니다.
//				주어진 시도, 구(군)에 있는 유명하거나 숨겨진 관광지와 숙박, 음식점을 추천해 주고
//				여행 계획을 세울 수 있게 관광지 사이의 이동 경로(최단거리와 컨텐츠타입의 조합)를 생각해서 1일 코스로 주어진 format에 맞게 응답해줘.
//				""");
//		// Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
//		// 바로 응답 메시지만 얻고 싶다면 Prompt 보다 바로 ...Message를 추천합니다.
//		String response = chatModel.call(userMessage, systemMessage);
//		System.out.println("response = " + response);
//		return "ok";
//	}
//
//}
//
