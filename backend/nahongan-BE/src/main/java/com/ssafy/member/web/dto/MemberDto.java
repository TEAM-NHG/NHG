package com.ssafy.member.web.dto;

import com.ssafy.member.persistent.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberDto {
	@Schema(description = "아이디")
	private String id;

	@Schema(description = "비밀번호")
	private String password;

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "이메일")
	private String email;

	@Schema(description = "전화번호")
	private String phone;

	@Schema(description = "프로필 이미지")
	private String img;

	@Schema(description = "댓글 알림")
	private int commentNotificationCount;

	public Member toEntity() {
		return Member
				.builder()
				.id(this.id)
				.password(this.password)
				.nickname(this.nickname)
				.email(this.email)
				.phone(this.phone)
				.img(this.img)
				.commentNotificationCount(this.commentNotificationCount)
				.build();
	}
}