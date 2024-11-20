package com.ssafy.tripinfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.tripinfo.web.dto.detail.CommonDetailDto;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailResult {
    private BodyDto body;
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BodyDto {
        private ItemsDto items;

        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ItemsDto {
            private List<CommonDetailDto> item;

        }
    }
}
