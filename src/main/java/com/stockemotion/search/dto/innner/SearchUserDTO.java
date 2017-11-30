package com.stockemotion.search.dto.innner;

import com.stockemotion.common.utils.JsonUtils;
import com.stockemotion.search.model.SearchUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by pigaunghua on 2016/12/6.
 */
@Document(indexName = "socialsix", type = "social_test", shards = 1 )
@Getter
@Setter
public class SearchUserDTO {
    @Id
    private Long id;
    @Field(ignoreFields = {"userId"} ,type= FieldType.Long)
    private Long userId;
    @Field(searchAnalyzer="ik", analyzer="ik", store=true, index = FieldIndex.not_analyzed, type= FieldType.String)
    private String nickName;

    private String pictureUrl;

    @Field(searchAnalyzer="analyzer", analyzer="analyzer", index = FieldIndex.not_analyzed, type= FieldType.String)
    private String cellphone;

    @Field(searchAnalyzer="ik", analyzer="ik" ,type= FieldType.String)
    private String introduce;

    public static SearchUserDTO SocialUser2SearchUser(SearchUser socialUser) {
        String jsonString = JsonUtils.TO_JSON(socialUser);
        SearchUserDTO searchUserDTO = (SearchUserDTO)JsonUtils.TO_OBJ(jsonString, SearchUserDTO.class);
        return  searchUserDTO;
    }



}
