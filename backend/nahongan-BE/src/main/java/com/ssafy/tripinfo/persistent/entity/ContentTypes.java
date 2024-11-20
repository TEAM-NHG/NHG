package com.ssafy.tripinfo.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContentTypes {
	int contentTypeId;
	String contentTypeName;
}
