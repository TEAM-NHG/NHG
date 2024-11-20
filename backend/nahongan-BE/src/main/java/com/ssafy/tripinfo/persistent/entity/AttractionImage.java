package com.ssafy.tripinfo.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttractionImage {
    int id;
    int contentId;
    String originimgurl;
}
