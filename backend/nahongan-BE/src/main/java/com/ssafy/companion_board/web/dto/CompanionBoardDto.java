package com.ssafy.companion_board.web.dto;

import java.time.LocalDateTime;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.member.web.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CompanionBoardDto {
    private int articleNo; // 게시글 번호
    private String userId; // 사용자 ID
    private String subject; // 제목
    private String content; // 내용
    private int hit; // 조회수
    private LocalDateTime registerTime; // 등록 시간
    
    public static CompanionBoardDto from(CompanionBoard article) {
        return CompanionBoardDto
                .builder()
                .articleNo(article.getArticleNo())
                .userId(article.getUserId())
                .subject(article.getSubject())
                .content(article.getContent())
                .hit(article.getHit())
                .registerTime(article.getRegisterTime())
                .build();
    }
}