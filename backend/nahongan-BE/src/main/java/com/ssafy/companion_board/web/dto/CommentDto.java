package com.ssafy.companion_board.web.dto;

import java.time.LocalDateTime;

import com.ssafy.companion_board.persistent.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CommentDto {

    private int id;            // 댓글 ID
    private int articleNo;     // 댓글이 달린 게시물 번호
    private final Boolean isOwner;
    private String userId;     // 댓글 작성자 ID
    private String content;    // 댓글 내용
    private LocalDateTime createdAt;  // 댓글 생성일
    private LocalDateTime updatedAt;  // 댓글 수정일

    /**
     * Entity로 변환하는 메서드
     */
    public Comment toEntity() {
        return Comment.builder()
                .id(this.id)
                .articleNo(this.articleNo)
                .userId(this.userId)
                .content(this.content)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    /**
     * Entity -> DTO 변환 메서드 (정적 메서드)
     * @param string 
     */
    public static CommentDto from(Comment comment) {
    	System.out.println(comment.getId());
    	System.out.println("----------");
        return CommentDto.builder()
                .id(comment.getId())
                .articleNo(comment.getArticleNo())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .isOwner(comment.isOwner("asdf"))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
