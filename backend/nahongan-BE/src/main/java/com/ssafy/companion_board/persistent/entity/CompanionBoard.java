package com.ssafy.companion_board.persistent.entity;

import java.time.LocalDateTime;

import com.ssafy.companion_board.web.dto.CompanionBoardDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CompanionBoard {
    private int articleNo; // 게시글 번호
    private String userId; // 사용자 ID
    private String subject; // 제목
    private String content; // 내용
    private int hit; // 조회수
    private LocalDateTime registerTime; // 등록 시간
    
	public void update(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
}