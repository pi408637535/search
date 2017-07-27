package com.stockemotion.search.dto;

import com.stockemotion.common.utils.JsonUtils;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by piguanghua on 6/22/17.
 */
@Setter
@Getter
public class MqUserDTO {
    private Long userId;
    private String nickName;


    public static MqUserDTO SearchUser2MqUserDTO(SearchUserDTO searchUserDTO) {
        String jsonString = JsonUtils.TO_JSON(searchUserDTO);
        MqUserDTO mqUserDTO = (MqUserDTO)JsonUtils.TO_OBJ(jsonString, MqUserDTO.class);
        return  mqUserDTO;
    }
}
