package com.ssafy.tripinfo.web.dto.detail;

public class DetailFactory {
    public static <T extends CommonDetailDto> T createDetailDTO(int contentTypeId) {
    	return switch (contentTypeId) {
        case 38 -> (T) new ShoppingDetailDto();  // ShoppingDetailDto 반환
        case 39 -> (T) new FoodDetailDto();      // FoodDetailDto 반환
        case 28 -> (T) new LeisureDetailDto();   // LeisureDetailDto 반환
        case 32 -> (T) new LodgingDetailDto();   // LodgingDetailDto 반환
        case 15 -> (T) new EventDetailDto();     // EventDetailDto 반환
        case 12 -> (T) new AttractionDetailDto(); // AttractionDetailDto 반환
        default -> (T) new CommonDetailDto();    // 기본값으로 CommonDetailDto 반환
    };
    }


}