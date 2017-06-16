package com.stockemotion.search.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stockemotion.common.utils.DateUtils;
import com.stockemotion.common.utils.HttpClientUtil;
import com.stockemotion.search.dao.SearchAutoIdDao;
import com.stockemotion.search.dao.SearchUserDao;
import com.stockemotion.search.dto.ContentDTO;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import com.stockemotion.search.es.dao.UserRepository;
import com.stockemotion.search.model.SearchAutoId;
import com.stockemotion.search.model.SearchUser;
import com.stockemotion.search.service.SearchUserService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.stockemotion.search.utils.SearchConstant.SOCIAL_CHECK_NICKNAME;

/**
 * Created by pigaunghua on 2016/12/6.
 */
@Service
public class SearchUserServiceImpl implements SearchUserService {

    @Resource
    private SearchUserDao searchUserDao;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SearchAutoIdDao searchAutoIdDao;

    @Override
    public void importAllUserToIndex() {
        List<SearchUser> listUser = searchUserDao.getListEntity(new SearchUser());

        List<SearchUserDTO> searchUserList = Lists.newArrayList();
        for(SearchUser searchUser : listUser){
            searchUserList.add(SearchUserDTO.SocialUser2SearchUser(searchUser));
        }
        userRepository.save(searchUserList);
    }


    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @Override
    public ContentDTO searchAll(String queryString, int page, int rows) {
        QueryBuilder builder = null;
        if (StringUtils.isEmpty(queryString)) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            builder = QueryBuilders.simpleQueryStringQuery(queryString);
        }
        Page<SearchUserDTO> searchUsers = userRepository.search(builder, new PageRequest(page, rows));

        List<SearchUserDTO> searchUserDTOS = Lists.newArrayList();
        for(SearchUserDTO searchUserDTO : searchUsers){
            searchUserDTOS.add(searchUserDTO);
        }

        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(searchUserDTOS);


        contentDTO.setTotalElements(searchUsers.getTotalElements());

        return contentDTO;
    }

    @Override
    public SearchUserDTO findOneById(Long userId) {
        SearchUserDTO searchUserDTO = userRepository.findOne(userId);
        return searchUserDTO;
    }

    @Override
    public String synchronizeUser(SearchUserDTO searchUserDTO) {

        SearchUser searchUser = SearchUser.SearchUserDTO2SearchUser(searchUserDTO);

        String nickName = searchUserDTO.getNickName();


        if(StringUtils.isBlank(nickName)){
            nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
            SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
            searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
            searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
            searchAutoIdDao.updateByPrimaryId(searchAutoId);
        }else {
            Map<String, String> params = Maps.newHashMap();
            nickName = HttpClientUtil.doGet(SOCIAL_CHECK_NICKNAME, params, new HashMap<>());
        }

        searchUser.setNickName(nickName);
        searchUserDTO.setNickName(nickName);

        Date dateNow = DateUtils.getCurrentTimestamp();
        Optional<SearchUser> searchUserOptional = searchUserDao.getEntityOne(searchUser);
        if(searchUserOptional.isPresent()){
            searchUserOptional.get().setSysUpdateTime(dateNow);
            searchUserDao.updateByPrimaryId(searchUserOptional.get());
        }else {
            searchUser.setSysUpdateTime(dateNow);
            searchUser.setSysCreateTime(dateNow);
            searchUserDao.insert(searchUser);
        }
        userRepository.index(searchUserDTO);
        return nickName;
    }


    @Override
    public String addUser(SearchUserDTO searchUserDTO) {

        String nickName = searchUserDTO.getNickName();
        if(StringUtils.isBlank(nickName)){
            nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
            SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
            searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
            searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
            searchAutoIdDao.updateByPrimaryId(searchAutoId);
        }else {
            Map<String, String> params = Maps.newHashMap();
            nickName = HttpClientUtil.doGet(SOCIAL_CHECK_NICKNAME, params, new HashMap<>());
        }

        SearchUser searchUser = SearchUser.SearchUserDTO2SearchUser(searchUserDTO);
        searchUser.setNickName(nickName);
        searchUserDTO.setNickName(nickName);

        Date dateNow = DateUtils.getCurrentTimestamp();
        searchUser.setSysUpdateTime(dateNow);
        searchUser.setSysCreateTime(dateNow);
        searchUserDao.insert(searchUser);
        userRepository.save(searchUserDTO);
        return nickName;
    }

    @Override
    public boolean searchByNickName(String nickName) {
        QueryBuilder builder = null;
        builder = QueryBuilders.termQuery("nickName", nickName);
        Iterable<SearchUserDTO> searchUsers = userRepository.search(builder, new PageRequest(0,1));
        Iterator<SearchUserDTO> iterator =  searchUsers.iterator();
        if (iterator.hasNext()){
            return true;
        }
        return false;
    }


}
