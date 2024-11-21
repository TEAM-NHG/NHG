package com.ssafy.tripinfo.persistent.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttractionImage {
    int id;
    int contentId;
    String originimgurl;

}
