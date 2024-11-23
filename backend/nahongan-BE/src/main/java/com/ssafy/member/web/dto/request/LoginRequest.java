package com.ssafy.member.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {
    @Schema(description = "사용자 아이디", example = "string")
    private String id;

    @Schema(description = "사용자 비밀번호", example = "string")
    private String password;
}