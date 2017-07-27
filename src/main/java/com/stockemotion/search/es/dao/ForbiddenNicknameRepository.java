package com.stockemotion.search.es.dao;

import com.stockemotion.search.dto.innner.ForbiddenNicknameDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by piguanghua on 7/27/17.
 */
public interface ForbiddenNicknameRepository extends ElasticsearchRepository<ForbiddenNicknameDTO, Long> {
}
