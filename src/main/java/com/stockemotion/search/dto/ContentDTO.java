package com.stockemotion.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by piguanghua on 3/23/17.
 */
@Setter
@Getter
public class ContentDTO {
    private List<?> content;
    private Long totalElements;
}
