package com.stockemotion.search.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stockemotion.common.utils.DateUtils;
import com.stockemotion.common.utils.HttpClientUtil;
import com.stockemotion.common.utils.JsonUtils;
import com.stockemotion.search.SearchApplication;
import com.stockemotion.search.dao.SearchAutoIdDao;
import com.stockemotion.search.dao.SearchUserDao;
import com.stockemotion.search.dto.ContentDTO;
import com.stockemotion.search.dto.MqUserDTO;
import com.stockemotion.search.dto.innner.ForbiddenNicknameDTO;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import com.stockemotion.search.es.dao.ForbiddenNicknameRepository;
import com.stockemotion.search.es.dao.UserRepository;
import com.stockemotion.search.mapper.SearchAdminUserMapper;
import com.stockemotion.search.model.SearchAdminUser;
import com.stockemotion.search.model.SearchAutoId;
import com.stockemotion.search.model.SearchUser;
import com.stockemotion.search.service.ForbidWordService;
import com.stockemotion.search.service.SearchUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.stockemotion.search.utils.SearchConstant.SOCIAL_CHECK_NICKNAME;

/**
 * Created by pigaunghua on 2016/12/6.
 */
@Slf4j
@Service
public class SearchUserServiceImpl implements SearchUserService {

    @Resource
    private SearchUserDao searchUserDao;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SearchAutoIdDao searchAutoIdDao;
    @Resource
    private ForbiddenNicknameRepository forbiddenNicknameRepository;
    @Resource
    private SearchAdminUserMapper searchAdminUserMapper;
    @Resource
    ForbidWordService forbidWordService;


    @Override
    public void importAllUserToIndex() {
        List<SearchUser> listUser = searchUserDao.getListEntity(new SearchUser());

        List<SearchUserDTO> searchUserList = Lists.newArrayList();
        for (SearchUser searchUser : listUser) {
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
        for (SearchUserDTO searchUserDTO : searchUsers) {
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

   /* @Override
    public String synchronizeUser(SearchUserDTO searchUserDTO) {

        SearchUser searchUser = SearchUser.SearchUserDTO2SearchUser(searchUserDTO);

        String nickName = searchUserDTO.getNickName();

        SearchUser searchUserQuery = new SearchUser();
        searchUserQuery.setUserId(searchUser.getUserId());
        Optional<SearchUser> searchUserOptional = searchUserDao.getEntityOne(searchUserQuery);


        if (!searchUserOptional.get().getNickName().equals(searchUserDTO.getNickName())) {
            if (StringUtils.isBlank(nickName) || this.searchByNickName(nickName)) {
                nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
                SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
                searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
                searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
                searchAutoIdDao.updateByPrimaryId(searchAutoId);
                return nickName;
            } else {
                Map<String, String> params = Maps.newHashMap();
                params.put("nickName", nickName);
               String nickNameNew = HttpClientUtil.doGet(SOCIAL_CHECK_NICKNAME, params, new HashMap<>());
                if(!StringUtils.isBlank(nickNameNew)){
                    nickName = nickNameNew;
                }
            }

        }


        searchUser.setNickName(nickName);
        searchUserDTO.setNickName(nickName);

        Date dateNow = DateUtils.getCurrentTimestamp();
        if (searchUserOptional.isPresent()) {
            searchUserOptional.get().setNickName(nickName);
            searchUserOptional.get().setSysUpdateTime(dateNow);
            searchUserDao.updateByPrimaryId(searchUserOptional.get());
        } else {
            searchUser.setSysUpdateTime(dateNow);
            searchUser.setSysCreateTime(dateNow);
            searchUserDao.insert(searchUser);
        }
        userRepository.index(searchUserDTO);

        return nickName;

    }
*/

    /*@Override
    public String addUser(SearchUserDTO searchUserDTO) {

        String nickName = searchUserDTO.getNickName();
        if (StringUtils.isBlank(nickName) || this.searchByNickName(nickName)) {
            nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
            SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
            searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
            searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
            searchAutoIdDao.updateByPrimaryId(searchAutoId);
            //防止二次调用
            return nickName;
        } else {
            Map<String, String> params = Maps.newHashMap();
            params.put("nickName", nickName);
            String nickNameNew = HttpClientUtil.doGet(SOCIAL_CHECK_NICKNAME, params, new HashMap<>());

            if(!StringUtils.isBlank(nickNameNew)){
                nickName = nickNameNew;
            }
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
    }*/

    @Override
    public String addUserOrUpdate(SearchUserDTO searchUserDTO) {
        String nickName = searchUserDTO.getNickName();

        SearchUser searchUserQuery = new SearchUser();
        searchUserQuery.setUserId(searchUserDTO.getUserId());
        Optional<SearchUser> searchUserOptional = searchUserDao.getEntityOne(searchUserQuery);
        if(searchUserOptional.isPresent()){ //exist user
            if(!searchUserOptional.get().getNickName().equals(nickName)){ //修改用户名

                QueryBuilder queryBuilder = new FuzzyQueryBuilder("nickName",nickName).boost(0.1f);

                Iterable<ForbiddenNicknameDTO> forbiddenNicknameDTOS = forbiddenNicknameRepository.search(queryBuilder);
                Iterator<ForbiddenNicknameDTO> forbiddenNicknameDTOIterator = forbiddenNicknameDTOS.iterator();

                boolean checkNickName = true; //默认名字可用
                if(forbiddenNicknameDTOIterator.hasNext())
                    checkNickName = false; // 不合格

               if(nickName.contains("沃"))
                   checkNickName = false; // 不合格


                if(!checkNickName) {
                    QueryBuilder builder = null;
                    builder = QueryBuilders.termQuery("nickName", nickName);
                    Iterable<SearchUserDTO> searchUsers = userRepository.search(builder, new PageRequest(0, 1));
                    Iterator<SearchUserDTO> iterator = searchUsers.iterator();
                    if (iterator.hasNext()) {
                        checkNickName = false;
                    }
                }


                //校验 权限用户
                List<SearchAdminUser> searchAdminUsers = searchAdminUserMapper.selectAll();
                for(SearchAdminUser searchAdminUser: searchAdminUsers){
                    if(searchAdminUser.getUserId().equals(searchUserOptional.get().getUserId()))
                        checkNickName = true;
                }

               /* Map<String, String> params = Maps.newHashMap();
                params.put("nickName", nickName);
                String nickNameNew = HttpClientUtil.doGet(SOCIAL_CHECK_NICKNAME, params, new HashMap<>());
                if(!StringUtils.isBlank(nickNameNew)){
                    if(nickNameNew != nickNameNew)
                        return nickNameNew;
                }*/
                if(!checkNickName){
                    nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
                    SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
                    searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
                    searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
                    searchAutoIdDao.updateByPrimaryId(searchAutoId);
                    return nickName;
                }
            }
            searchUserOptional.get().setNickName(nickName);
            searchUserOptional.get().setIntroduce(searchUserDTO.getIntroduce());
            searchUserOptional.get().setCellphone(searchUserDTO.getCellphone());
            searchUserOptional.get().setPictureUrl(searchUserDTO.getPictureUrl());
            Date dateNow = DateUtils.getCurrentTimestamp();
            searchUserOptional.get().setSysUpdateTime(dateNow);

           // log.info("update=" + JsonUtils.TO_JSON(searchUserOptional.get()));

            searchUserDao.updateByPrimaryId(searchUserOptional.get());
            userRepository.delete(searchUserOptional.get().getId());
            SearchUserDTO searchUserDTOExist = userRepository.save(searchUserDTO);
            log.info("searchUserDTOExist  exist" + JsonUtils.TO_JSON(searchUserDTOExist));

        }else{ //new user
            if (StringUtils.isBlank(nickName) || this.searchByNickName(nickName) || !nickName.equals(forbidWordService.fillForbidWord(nickName)) ) {
                nickName = "匿名用户" + searchAutoIdDao.getByPrimary(1L).get().getAutoId();
                SearchAutoId searchAutoId = searchAutoIdDao.getByPrimary(1L).get();
                searchAutoId.setSysUpdateTime(DateUtils.getCurrentTimestamp());
                searchAutoId.setAutoId(searchAutoId.getAutoId() + 1);
                searchAutoIdDao.updateByPrimaryId(searchAutoId);
            }

            SearchUser searchUser = SearchUser.SearchUserDTO2SearchUser(searchUserDTO);
            searchUser.setNickName(nickName);
            searchUserDTO.setNickName(nickName);

            Date dateNow = DateUtils.getCurrentTimestamp();
            searchUser.setSysUpdateTime(dateNow);
            searchUser.setSysCreateTime(dateNow);
            searchUserDao.insert(searchUser);
            SearchUserDTO searchUserDTOExist = userRepository.save(searchUserDTO);
            log.info("searchUserDTOExist  now" + JsonUtils.TO_JSON(searchUserDTOExist));
        }

        return nickName;
    }

    /**
     * 前端做这个仅仅是查重名
     * 不重名 false 重名 true
     * @param nickName
     * @return
     */
    @Override
    public boolean searchByNickName(String nickName) {
        QueryBuilder builder = null;
        builder = QueryBuilders.termQuery("nickName", nickName);
        Iterable<SearchUserDTO> searchUsers = userRepository.search(builder, new PageRequest(0, 1));
        Iterator<SearchUserDTO> iterator = searchUsers.iterator();
        if (iterator.hasNext()) {
            return true;
        }
        return false;
    }

    @Override
    public MqUserDTO searchUserByNickName(String nickName) {
        QueryBuilder builder = null;
        builder = QueryBuilders.termQuery("nickName", nickName);
        Iterable<SearchUserDTO> searchUsers = userRepository.search(builder, new PageRequest(0,1));
        Iterator<SearchUserDTO> iterator =  searchUsers.iterator();
        if (iterator.hasNext()){
            MqUserDTO mqUserDTO = MqUserDTO.SearchUser2MqUserDTO(iterator.next());
            return mqUserDTO;
        }
        return null;
    }

    @Override
    public synchronized boolean importForbiddenNickname() {
        InputStream in = SearchApplication.class.getResourceAsStream("/mydic.dic");
        try {
            List<String> list = IOUtils.readLines(in);
            ForbiddenNicknameDTO forbiddenNicknameDTO = new ForbiddenNicknameDTO();
            for(Long i = 0L; i < list.size(); i++){
                forbiddenNicknameDTO.setId(i);
                forbiddenNicknameDTO.setNickName(list.get(i.intValue()));
                forbiddenNicknameRepository.save(forbiddenNicknameDTO);
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }


}
