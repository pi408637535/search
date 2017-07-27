package com.stockemotion.search.service;

import com.stockemotion.search.dto.ContentDTO;
import com.stockemotion.search.dto.MqUserDTO;
import com.stockemotion.search.dto.innner.SearchUserDTO;


/**
 * Created by pigaunghua on 2016/12/6.
 */
public interface SearchUserService {
    void importAllUserToIndex();
    void deleteAllUser();
    ContentDTO searchAll(String queryString, int page, int rows);

    //前端在股友会进行查询用户
    SearchUserDTO findOneById(Long userId);  //社交平台ID

    //仅仅在于用户更新了个人信息
    /**
     * addUserOrUpdate
     * @param searchUserDTO
     * @return
     */
  /*  @Deprecated
    String synchronizeUser(SearchUserDTO searchUserDTO); //用于更新用户
*/

    //新用户生成

    /**
     * addUserOrUpdate
     * @param searchUserDTO
     * @return
     */
  /*  @Deprecated
    String addUser(SearchUserDTO searchUserDTO); //用于更新用户
*/
    String addUserOrUpdate(SearchUserDTO searchUserDTO); //用于更新用户


    //前端用于修改用户名
    boolean searchByNickName(String nickName); //用于更新用户

    MqUserDTO searchUserByNickName(String nickName); //用于更新用户

    boolean importForbiddenNickname();

}
