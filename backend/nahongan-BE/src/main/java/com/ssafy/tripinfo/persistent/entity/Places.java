package com.ssafy.tripinfo.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Places {
	String address;
	String category;
	int gugunNo;
	int id;
	String link;
	float mapx;
	float mapy;
	String roadAddress;
	String title;
}
