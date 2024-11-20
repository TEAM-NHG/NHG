package com.ssafy.tripinfo.persistent.entity;

import java.util.List;

import com.ssafy.member.persistent.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attractions {
	int no;
	int contentId;
	String title;
	int contentTypeId;
	int areaCode;
	int sigunguCode;
	int mapLevel;
	double latitude;
	double longitude;
	String tel;
	String addr1;
	String addr2;
	String homepage;
	String overview;
	String parking;
	String time;
	boolean isImageSaved;
	boolean isDetailSaved;
	List<String> images;
}
