package com.ssafy.member.web.dto;

import com.ssafy.member.persistent.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class MemberDto {
	@Schema(description = "아이디")
	private final String id;

	@Schema(description = "비밀번호")
	private final String password;

	@Schema(description = "닉네임")
	private final String nickname;

	@Schema(description = "이메일")
	private final String email;

	@Schema(description = "전화번호")
	private final String phone;

	@Schema(description = "프로필 이미지")
	private final String img;

	public Member toEntity() {
		return Member
				.builder()
				.id(this.id)
				.password(this.password)
				.nickname(this.nickname)
				.email(this.email)
				.phone(this.phone)
				.img(this.img)
				.build();
	}
}