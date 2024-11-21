package com.ssafy.companion_board.web.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateChildCommentRequest extends CreateCommentRequest {
	private Integer parentCommentId;
	public Integer getParentCommentId() {
		// TODO Auto-generated method stub
		return this.parentCommentId;
	} 
}
