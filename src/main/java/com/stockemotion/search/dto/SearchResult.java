package com.stockemotion.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by pigaunghua on 2016/12/10.
 */
@Getter
@Setter
public class SearchResult {
    @JsonProperty("searchUser")
    private Iterable<SearchUserDTO> itemList;
    private Long recordCount;
    private int pageCount;
    private int curPage;

}
