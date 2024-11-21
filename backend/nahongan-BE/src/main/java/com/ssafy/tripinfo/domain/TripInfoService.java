package com.ssafy.tripinfo.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripinfo.persistent.entity.AttractionImage;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.persistent.repository.TripInfoRepository;
import com.ssafy.tripinfo.web.dto.detail.AttractionDetailDto;
import com.ssafy.tripinfo.web.dto.detail.CommonDetailDto;
import com.ssafy.tripinfo.web.dto.detail.EventDetailDto;
import com.ssafy.tripinfo.web.dto.detail.FoodDetailDto;
import com.ssafy.tripinfo.web.dto.detail.LeisureDetailDto;
import com.ssafy.tripinfo.web.dto.detail.LodgingDetailDto;
import com.ssafy.tripinfo.web.dto.detail.ShoppingDetailDto;
import com.ssafy.tripinfo.web.dto.request.GetGugunBySidoCodeRequest;
import com.ssafy.tripinfo.web.dto.request.SearchAttractionRequest;
import com.ssafy.tripinfo.web.dto.request.SearchBlogRequest;
import com.ssafy.tripinfo.web.dto.response.GetFilterContentResponse;
import com.ssafy.tripinfo.web.dto.response.GetGugunBySidoCodeResponse;
import com.ssafy.tripinfo.web.dto.response.SearchAttractionResponse;
import com.ssafy.tripinfo.web.dto.response.SearchBlogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TripInfoService {

    private final TripInfoRepository tripInfoRepository;

	private final NaverApiService naverApiService;

	private final TourApiService tourApiService;

    int ATTRACTION_LIST_SIZE = 12;

	int BLOG_LIST_SIZE = 5;
    
    public SearchAttractionResponse searchAttraction(SearchAttractionRequest request, int pgno) throws Exception {
    	
    	int offset = ATTRACTION_LIST_SIZE*(pgno-1);
    	List<Attractions> attractions = tripInfoRepository.findAttractionByFilter(request.getSidoCode(), request.getGugunCode(), request.getType(), request.getKeyword(), offset, ATTRACTION_LIST_SIZE);
    	int count = tripInfoRepository.countAttraction(request.getSidoCode(), request.getGugunCode(), request.getType(), request.getKeyword());
    	System.out.println(count);
		for (Attractions attraction:attractions) {
			//이미지가 저장안됐다면?
			if (!attraction.isImageSaved()) {
				//이미지 api 요청 후 엔터티에 삽입
				attraction.setImages(tourApiService.searchImages(attraction.getContentId()).getImages());
				//이미지 저장한 후 true로 변경
				attraction.setImageSaved(true);
				//attraction 변경
				tripInfoRepository.modifyAttractions(attraction);
				//images 생성
				List<AttractionImage> images = new ArrayList<>();

				for (String image:attraction.getImages()) {
					images.add(AttractionImage.builder().originimgurl(image).contentId(attraction.getContentId()).build());
				}
				if (images.isEmpty()) {
					continue;
				}
				tripInfoRepository.saveImages(images);
				attractions = tripInfoRepository.findAttractionByFilter(request.getSidoCode(), request.getGugunCode(), request.getType(), request.getKeyword(), offset, ATTRACTION_LIST_SIZE);
			}
		}
        return SearchAttractionResponse.builder()
		.attractionList(attractions)
		.lastPage(count/ATTRACTION_LIST_SIZE+1)
		.currentPage(pgno)
		.build();
    }
    
	public GetFilterContentResponse getFilterContent() throws Exception {
		return GetFilterContentResponse.builder()
				.contentTypeList(tripInfoRepository.findAllContentType())
				.sidoList(tripInfoRepository.findAllSido())
				.build();
	}
    
	public GetGugunBySidoCodeResponse getGugunBySidoCode(GetGugunBySidoCodeRequest request) throws Exception {
		return GetGugunBySidoCodeResponse.builder()
			.gugunList(tripInfoRepository.findGugunBySido(request.getSidoCode()))
			.build();
    }

    public SearchBlogResponse searchBlog(SearchBlogRequest request, int pgno) {
    	String keyword = "";
    	if (request.getKeyword() == null || request.getKeyword() == "") {
    		keyword = "혼자여행";
    	} else {
    		keyword = request.getKeyword();
    	}
		return SearchBlogResponse.builder()
				.blogList(naverApiService.searchBlog(keyword, BLOG_LIST_SIZE, pgno, "sim"))
				.currentPage(pgno)
				.build();
    }

	public Attractions getAttractionDetail(int no) throws Exception {
		Attractions attraction = tripInfoRepository.findAttractionByNo(no);
		//상세정보 저장 여부가 false면 상세정보 api 요청
		if (!attraction.isDetailSaved()) {
			CommonDetailDto detailDto = tourApiService.searchDetail(attraction.getContentId(), attraction.getContentTypeId());
			//각각 찾고난 후 attraction에 삽입해주기
			attraction.setAddr1(detailDto.getAddr1());
			attraction.setAddr2(detailDto.getAddr2());
			attraction.setOverview(detailDto.getOverview());
			attraction.setTel(detailDto.getTel());
			attraction.setHomepage(detailDto.getHomepage());
			if (detailDto instanceof ShoppingDetailDto) {
                attraction.setParking(((ShoppingDetailDto) detailDto).getParkingshopping());
				attraction.setTime(((ShoppingDetailDto) detailDto).getOpentime());
			}
			if (detailDto instanceof EventDetailDto) {
				attraction.setTime(((EventDetailDto) detailDto).getPlaytime());
			}
			if (detailDto instanceof AttractionDetailDto) {
				attraction.setParking(((AttractionDetailDto) detailDto).getParking());
			}
			if (detailDto instanceof FoodDetailDto) {
				attraction.setParking(((FoodDetailDto) detailDto).getParkingfood());
				attraction.setTime(((FoodDetailDto) detailDto).getOpentimefood());
			}
			if (detailDto instanceof LeisureDetailDto) {
				attraction.setParking(((LeisureDetailDto) detailDto).getParkingleports());
				attraction.setTime(((LeisureDetailDto) detailDto).getUsetimeleports());
			}
			if (detailDto instanceof LodgingDetailDto) {
				attraction.setParking(((LodgingDetailDto) detailDto).getParkinglodging());
			}
			attraction.setDetailSaved(true);
			tripInfoRepository.modifyAttractions(attraction);
		}
		return attraction;
	}

}
