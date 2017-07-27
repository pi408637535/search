package com.stockemotion.search.dto.innner;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by piguanghua on 7/27/17.
 */
@Document(indexName = "forbidden", type = "social_nickname", shards = 1 )
@Getter
@Setter
public class ForbiddenNicknameDTO {
    @Id
    private Long id;
     @Field(searchAnalyzer="ik", analyzer="ik", store=true, index = FieldIndex.not_analyzed, type= FieldType.String)
    private String nickName;

    @Override
    public String toString() {
        return "UserNicknameDTO{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
