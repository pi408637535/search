package com.stockemotion.search.dao.impl;

import com.stockemotion.search.dao.SearchUserDao;
import com.stockemotion.search.mapper.SearchUserMapper;
import com.stockemotion.search.model.SearchUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * Created by piguanghua on 6/16/17.
 */
@Repository
public class SearchUserDaoImpl extends BaseDaoImpl<SearchUser>  implements SearchUserDao {

    @Resource
    SearchUserMapper searchUserMapper;

    @Override
    public Mapper<SearchUser> getMapper() {
        return searchUserMapper;
    }
}
