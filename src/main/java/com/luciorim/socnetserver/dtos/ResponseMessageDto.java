package com.luciorim.socnetserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessageDto {

    private Long id;
    private String text;
    private String link;
    private String linkDescription;
    private String linkTitle;
    private String linkCover;
}
