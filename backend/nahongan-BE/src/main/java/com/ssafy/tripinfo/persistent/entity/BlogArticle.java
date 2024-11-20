package com.ssafy.tripinfo.persistent.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BlogArticle {
	String bloggerlink;
	String bloggername;
	Date createdDate;
	String description;
	String link;
	String title;
}
