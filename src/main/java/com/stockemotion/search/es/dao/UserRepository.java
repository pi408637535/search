package com.stockemotion.search.es.dao;


import com.stockemotion.search.dto.innner.SearchUserDTO;
import com.stockemotion.search.model.SearchUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by wode3 on 2016/11/25.
 */
public interface UserRepository extends ElasticsearchRepository<SearchUserDTO, Long> {
}
