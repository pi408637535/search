package com.stockemotion.search.dao.impl;

import com.stockemotion.search.dao.SearchAutoIdDao;
import com.stockemotion.search.mapper.SearchAutoIdMapper;
import com.stockemotion.search.model.SearchAutoId;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * Created by piguanghua on 6/16/17.
 */
@Repository
public class SearchAutoIdDaoImpl extends BaseDaoImpl<SearchAutoId> implements SearchAutoIdDao {

    @Resource
    SearchAutoIdMapper searchAutoIdMapper;


    @Override
    public Mapper<SearchAutoId> getMapper() {
        return searchAutoIdMapper;
    }
}
