package com.ssafy.tripinfo.web.controller;

import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.web.dto.detail.CommonDetailDto;
import com.ssafy.tripinfo.web.dto.request.SearchBlogRequest;
import com.ssafy.tripinfo.web.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.tripinfo.domain.TripInfoService;
import com.ssafy.tripinfo.web.dto.request.GetGugunBySidoCodeRequest;
import com.ssafy.tripinfo.web.dto.request.SearchAttractionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "여행정보 API", description = "여행정보 필터, 블로그, 카드목록 등의 API")
@RequestMapping("/api/trip-info")
public class TripInfoController {

	private final TripInfoService tripInfoService;

	@GetMapping("/search")
	@Operation(
			summary = "관광지, 숙소 등 카드 정보 불러오는 API", // 요약
			description = "동시에 이미지도 호출" // 상세 설명
	)
	public ResponseEntity<SearchAttractionResponse> searchAttraction(SearchAttractionRequest request, @RequestParam(value="pgno", required=false, defaultValue = "1") int pgno) throws Exception {
		long start = System.currentTimeMillis();
		SearchAttractionResponse responseBody = tripInfoService.searchAttraction(request, pgno);
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

	@Operation(
			summary = "카드 모달의 상세정보를 불러오는 API", // 요약
			description = "투어 API를 호출하거나 DB에서 호출" // 상세 설명
	)
	@GetMapping("/search/attraction/{no}")
	public ResponseEntity<Attractions> getAttractionDetail(@PathVariable("no") int contentNo) throws Exception {
		long start = System.currentTimeMillis();
		Attractions responseBody = tripInfoService.getAttractionDetail(contentNo);
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@Operation(
			summary = "블로그 게시글 API", // 요약
			description = "DEFAULT : 혼자 여행, 카드 모달에서 이동 시 해당 어트랙션으로 검색" // 상세 설명
	)
	@GetMapping("/search/blog")
	public ResponseEntity<SearchBlogResponse> searchBlog(SearchBlogRequest request, @RequestParam(value="pgno", required=false, defaultValue = "1") int pgno) throws Exception {
		return new ResponseEntity<>(tripInfoService.searchBlog(request, pgno), HttpStatus.OK);
	}
	@Operation(
			summary = "여행정보 검색 필터 API(시도, 관광지 유형)", // 요약
			description = "시도, 관광지 유형의 필터 정보 호출. " // 상세 설명
	)
	@GetMapping("/filter")
	public ResponseEntity<GetFilterContentResponse> getFilterContent() throws Exception {
		return new ResponseEntity<>(tripInfoService.getFilterContent(), HttpStatus.OK);
	}
	
	@Operation(
			summary = "구군 검색 필터 API", // 요약
			description = "시도를 통해 구군 정보 호출. " // 상세 설명
	)
	@GetMapping("/filter/gugun")
	public ResponseEntity<GetGugunBySidoCodeResponse> getGugunBySidoCode(GetGugunBySidoCodeRequest request) throws Exception {
        return new ResponseEntity<>(tripInfoService.getGugunBySidoCode(request), HttpStatus.OK);
    }
}
