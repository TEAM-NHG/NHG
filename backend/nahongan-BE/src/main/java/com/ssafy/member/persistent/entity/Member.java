package com.ssafy.member.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {
	String id;
	String nickname;
	String phone;
	String email;
	String password;
	String img;
	String role;
	int commentNotificationCount;

	public void hashPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(plainPassword, this.password);
	}

	public void updateImage(String imagePath) {
		this.img = imagePath;
	}

}
