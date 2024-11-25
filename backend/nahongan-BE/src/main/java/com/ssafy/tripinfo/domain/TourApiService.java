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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TourApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${tourapi.key}")
    private String serviceKey;

    private String requestApi(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) stream.close();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
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
        System.out.println("uri 테스트");
        System.out.println(diffUriString);
        System.out.println(commonUriString);
        CommonDetailDto detailDto = DetailFactory.createDetailDTO(contentTypeId);
        return detailResponseParser(requestApi(commonUriString), requestApi(diffUriString), detailDto, contentTypeId);
    }


    private CommonDetailDto detailResponseParser(String response1, String response2, CommonDetailDto detailDto, int contentTypeId) {
        System.out.println(response1);
        System.out.println(response2);
        JsonNode jsonNode = null;
        CommonDetailDto result1 = DetailFactory.createDetailDTO(contentTypeId);
        try {
        	jsonNode = objectMapper.readTree(response1);
            JsonNode itemsNode = jsonNode.get("response").get("body").get("items");
            if (itemsNode != null) {
                // "item" 배열에 접근
                JsonNode itemNode = itemsNode.get("item").get(0);
                result1 = objectMapper.treeToValue(itemNode, CommonDetailDto.class);
                System.out.println(result1);
                // 각 필드를 수동으로 설정
            }
        	
            jsonNode = objectMapper.readTree(response2);
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
        System.out.println(imageUri.toString());
        AttractionImageDtos dtos = AttractionImageDtos.builder()
                                .images(new ArrayList<>())
                                .build();

        imageResponseParser(requestApi(imageUriString), dtos);

        return dtos;
    }

    private void imageResponseParser(String response, AttractionImageDtos attractionImageDtos) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response);
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

    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException{
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }
}
