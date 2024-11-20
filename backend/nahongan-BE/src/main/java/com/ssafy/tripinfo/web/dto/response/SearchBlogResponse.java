package com.ssafy.tripinfo.web.dto.response;

import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.persistent.entity.BlogArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SearchBlogResponse {
    List<BlogArticle> blogList;
    int currentPage;
}
