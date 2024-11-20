package com.ssafy.tripinfo.web.controller;

import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.web.dto.detail.CommonDetailDto;
import com.ssafy.tripinfo.web.dto.request.SearchBlogRequest;
import com.ssafy.tripinfo.web.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.tripinfo.domain.TripInfoService;
import com.ssafy.tripinfo.web.dto.request.GetGugunBySidoCodeRequest;
import com.ssafy.tripinfo.web.dto.request.SearchAttractionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-info")
public class TripInfoController {

	private final TripInfoService tripInfoService;

	@GetMapping("/search")
	public ResponseEntity<SearchAttractionResponse> searchAttraction(SearchAttractionRequest request, @RequestParam(value="pgno", required=false, defaultValue = "1") int pgno) throws Exception {
		long start = System.currentTimeMillis();
		SearchAttractionResponse responseBody = tripInfoService.searchAttraction(request, pgno);
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

	@GetMapping("/search/attraction/{no}")
	public ResponseEntity<Attractions> getAttractionDetail(@PathVariable("no") int contentNo) throws Exception {
		long start = System.currentTimeMillis();
		Attractions responseBody = tripInfoService.getAttractionDetail(contentNo);
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@GetMapping("/search/blog")
	public ResponseEntity<SearchBlogResponse> searchBlog(SearchBlogRequest request, @RequestParam(value="pgno", required=false, defaultValue = "1") int pgno) throws Exception {
		return new ResponseEntity<>(tripInfoService.searchBlog(request, pgno), HttpStatus.OK);
	}
    
	@GetMapping("/filter")
	public ResponseEntity<GetFilterContentResponse> getFilterContent() throws Exception {
		return new ResponseEntity<>(tripInfoService.getFilterContent(), HttpStatus.OK);
	}
    
	@GetMapping("/filter/gugun")
	public ResponseEntity<GetGugunBySidoCodeResponse> getGugunBySidoCode(GetGugunBySidoCodeRequest request) throws Exception {
        return new ResponseEntity<>(tripInfoService.getGugunBySidoCode(request), HttpStatus.OK);
    }
}
