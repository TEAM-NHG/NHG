package com.ssafy.companion_board.web.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.ssafy.companion_board.persistent.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@SuperBuilder
public class ParentCommentDto extends CommentDto {

	List<CommentDto> replies;
	
	public void addReply(CommentDto commentDto) {
		this.replies.add(commentDto);
	}
	
	public static ParentCommentDto from(Comment comment, String userId) {
    	System.out.println(comment.getId());
    	System.out.println("----------");
        return ParentCommentDto.builder()
                .id(comment.getId())
                .articleNo(comment.getArticleNo())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .isOwner(comment.isOwner(userId))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .replies(new ArrayList<CommentDto>())
                .isArticleOwnerRead(comment.isArticleOwnerRead())
                .isCommentOwnerRead(comment.isCommentOwnerRead())
                .build();
    }
}