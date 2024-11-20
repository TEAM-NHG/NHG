package com.ssafy.tripinfo.web.dto.response;

import java.util.List;
import com.ssafy.tripinfo.persistent.entity.Attractions;

import com.ssafy.tripinfo.web.dto.detail.AttractionImageDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SearchAttractionResponse {
	List<Attractions> attractionList;
	int currentPage;
	int lastPage;
}
