package com.ssafy.tripinfo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripinfo.web.dto.detail.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TourApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${tourapi.key}")
    private String serviceKey;

    private ResponseEntity<String> requestApi(URI uri) {
    	System.out.println(uri);
        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .build();
        return restTemplate.exchange(requestEntity, String.class);
    }

    public CommonDetailDto searchDetail(int contentId, int contentTypeId) throws UnsupportedEncodingException {
        String diffUriString = UriComponentsBuilder
                .fromUriString("https://apis.data.go.kr/B551011/KorService1/detailIntro1")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "nahongan")
                .queryParam("serviceKey", serviceKey)  // 인코딩된 serviceKey 사용
                .queryParam("contentId", contentId)
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("_type", "json")
                .build()
                .toString();
        String commonUriString = UriComponentsBuilder
        		.fromUriString("https://apis.data.go.kr/B551011/KorService1/detailCommon1")
        		.queryParam("MobileOS", "ETC")
        		.queryParam("MobileApp", "nahongan")
        		.queryParam("serviceKey", serviceKey)
        		.queryParam("contentId", contentId)
        		.queryParam("contentTypeId", contentTypeId)
        		.queryParam("_type", "json")
        		.queryParam("defaultYN", "Y")
        		.queryParam("overviewYN", "Y")
        		.queryParam("addrinfoYN", "Y")
        		.build()
        		.toString();
        URI diffUri = URI.create(diffUriString);
        URI commonUri = URI.create(commonUriString);
        CommonDetailDto detailDto = DetailFactory.createDetailDTO(contentTypeId);
        return detailResponseParser(requestApi(commonUri), requestApi(diffUri), detailDto, contentTypeId);
    }


    private CommonDetailDto detailResponseParser(ResponseEntity<String> response1, ResponseEntity<String> response2, CommonDetailDto detailDto, int contentTypeId) {
        JsonNode jsonNode = null;
        CommonDetailDto result1 = DetailFactory.createDetailDTO(contentTypeId);
        try {
        	jsonNode = objectMapper.readTree(response1.getBody());
            JsonNode itemsNode = jsonNode.get("response").get("body").get("items");
            if (itemsNode != null) {
                // "item" 배열에 접근
                JsonNode itemNode = itemsNode.get("item").get(0);
                result1 = objectMapper.treeToValue(itemNode, CommonDetailDto.class);
                System.out.println(result1);
                // 각 필드를 수동으로 설정
            }
        	
            jsonNode = objectMapper.readTree(response2.getBody());
            itemsNode = jsonNode.get("response").get("body").get("items");
            if (itemsNode != null) {
                // "item" 배열에 접근
                JsonNode itemNode = itemsNode.get("item").get(0);
                switch (itemNode.get("contenttypeid").asText()) {
                    case "38":
                        detailDto = objectMapper.treeToValue(itemNode, ShoppingDetailDto.class);
                        break;
                    case "39":
                        detailDto = objectMapper.treeToValue(itemNode, FoodDetailDto.class);
                        break;
                    case "28":
                        detailDto = objectMapper.treeToValue(itemNode, LeisureDetailDto.class);
                        break;
                    case "32":
                        detailDto = objectMapper.treeToValue(itemNode, LodgingDetailDto.class);
                        break;
                    case "15":
                        detailDto = objectMapper.treeToValue(itemNode, EventDetailDto.class);
                        break;
                    case "12":
                        detailDto = objectMapper.treeToValue(itemNode, AttractionDetailDto.class);
                        break;
                    default:
                        detailDto = objectMapper.treeToValue(itemNode, CommonDetailDto.class);
                        break;
                }
                detailDto.setContentid(result1.getContentid());
                detailDto.setContenttypeid(result1.getContenttypeid());
                detailDto.setTitle(result1.getTitle());
                detailDto.setHomepage(result1.getHomepage());
                detailDto.setTel(result1.getTel());
                detailDto.setOverview(result1.getOverview());
                detailDto.setAddr1(result1.getAddr1());
                detailDto.setAddr2(result1.getAddr2());
            }
        } catch (JsonProcessingException e) {
            e.fillInStackTrace();
        }
        System.out.println(detailDto);
        return detailDto;
    }

    public AttractionImageDtos searchImages(int contentId) {
        String imageUriString = UriComponentsBuilder
                .fromUriString("https://apis.data.go.kr/B551011/KorService1/detailImage1")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "nahongan")
                .queryParam("serviceKey", serviceKey)
                .queryParam("contentId", contentId)
                .queryParam("_type", "json")
                .queryParam("imageYN", "Y")
                .queryParam("subImageYN", "Y")
                .build()
                .toString();
        URI imageUri = URI.create(imageUriString);
        AttractionImageDtos dtos = AttractionImageDtos.builder()
                                .images(new ArrayList<>())
                                .build();

        imageResponseParser(requestApi(imageUri), dtos);

        return dtos;
    }

    private void imageResponseParser(ResponseEntity<String> response, AttractionImageDtos attractionImageDtos) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = jsonNode.get("response").get("body").get("items");
            JsonNode cntNode = jsonNode.get("response").get("body").get("numOfRows");
            if (cntNode.asInt() != 0) {
                // "item" 배열에 접근
                int i = 0;
                JsonNode itemNode = itemsNode.get("item");
                while (i<cntNode.asInt()) {
                    attractionImageDtos.getImages().add(objectMapper.treeToValue(itemNode.get(i++), AttractionImageDto.class).getOriginimgurl());
                }
            }
        }
        catch (JsonProcessingException e) {
            e.fillInStackTrace();
        }
    }
}
