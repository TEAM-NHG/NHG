package com.ssafy.companion_board.web.dto.request;

import java.util.List;
import com.ssafy.tripinfo.persistent.entity.Attractions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DeleteArticleRequest {
	int articleNo;
}
