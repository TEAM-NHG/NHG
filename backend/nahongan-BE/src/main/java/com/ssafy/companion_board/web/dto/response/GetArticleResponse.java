package com.ssafy.companion_board.web.dto.response;

import java.time.LocalDateTime;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetArticleResponse {
	private int articleNo; // 게시글 번호
    private String userId; // 사용자 ID
    private String nickname;
    private String image;
    private String subject; // 제목
    private String content; // 내용
    private int hit; // 조회수
    private LocalDateTime registerTime; // 등록 시간
    private int commentCount;
    
    public static GetArticleResponse from(CompanionBoard article, String nickname) {
        return GetArticleResponse
                .builder()
                .articleNo(article.getArticleNo())
                .userId(article.getUserId())
                .nickname(nickname)
                .subject(article.getSubject())
                .content(article.getContent())
                .hit(article.getHit())
                .registerTime(article.getRegisterTime())
                .build();
    }
}
