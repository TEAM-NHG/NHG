package com.ssafy.member.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member implements UserDetails {
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 정보를 반환
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {

				return role;
			}
		});
		return collection;
	}

	public void updateImage(String imagePath) {
		this.img = imagePath;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
