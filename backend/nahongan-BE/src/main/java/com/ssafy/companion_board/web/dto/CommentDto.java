package com.ssafy.companion_board.web.dto;

import java.time.LocalDateTime;

import com.ssafy.companion_board.persistent.entity.Comment;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    private int id;            // 댓글 ID
    private int articleNo;     // 댓글이 달린 게시물 번호
    private Boolean isOwner;
    private String userId;     // 댓글 작성자 ID
    private int parentId;
    private String content;    // 댓글 내용
    private LocalDateTime createdAt;  // 댓글 생성일
    private LocalDateTime updatedAt;  // 댓글 수정일
    private boolean isArticleOwnerRead; 
    private boolean isCommentOwnerRead;
    private String image;
    private String nickname;

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
                .isArticleOwnerRead(this.isArticleOwnerRead)
                .isCommentOwnerRead(this.isCommentOwnerRead)
                .build();
    }

    public static CommentDto from(Comment comment, String userId) {
    	System.out.println(comment.getId());
    	System.out.println("----------");
        return CommentDto.builder()
                .id(comment.getId())
                .articleNo(comment.getArticleNo())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .isOwner(comment.isOwner(userId))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
