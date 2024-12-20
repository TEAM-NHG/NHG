package com.ssafy.companion_board.persistent.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Comment {
	private int id;         // 댓글 ID
    private int articleNo;     // 댓글이 달린 게시물 번호
    private String userId;     // 댓글 작성자 ID
    private String content;    // 댓글 내용
    private Integer parentId;
    private LocalDateTime createdAt;  // 댓글 생성일
    private LocalDateTime updatedAt;  // 댓글 수정일
    private boolean isArticleOwnerRead; 
    private boolean isCommentOwnerRead;

	public void updateContent(String content) {
		this.content = content;
	}

	public Boolean isOwner(String userId) {
		return this.userId.equals(userId);
	}
	
	public boolean isParent() {
		if (this.parentId == null) {
			return true;
		} else {
			return false;
		}
	}
}