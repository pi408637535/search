package com.stockemotion.search.controller;


import com.stockemotion.common.http.ResponseBody;
import com.stockemotion.search.dto.ContentDTO;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import com.stockemotion.search.es.dao.UserRepository;
import com.stockemotion.search.service.SearchUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by wode4 on 2016/10/19.
 */
@RestController()
@RequestMapping("/wd/search/user")
public class UserController extends BaseController
{
    @Resource
    private SearchUserService searchUserService;
    @Resource
    private UserRepository userRepository;


    @RequestMapping("/importAll")
    public ResponseBody importAll(){
        searchUserService.importAllUserToIndex();
        return getResponseBody("ok");
    }


    @RequestMapping("/deleteAll")
    public ResponseBody deleteAll(){
        searchUserService.deleteAllUser();
        return getResponseBody("ok");
    }

    @RequestMapping("/q")
    public ResponseBody search(@RequestParam(defaultValue="")String keyword,
        @RequestParam(defaultValue="1" )Integer page,
        @RequestParam(defaultValue="15")Integer rows){

        ContentDTO contentDTO = searchUserService.searchAll(keyword, page-1, rows);

        return getResponseBody(contentDTO);
    }

    @RequestMapping("/getOne")
    public ResponseBody search(Long  userId){

        return getResponseBody(searchUserService.findOneById(userId));
    }

    //前端检测唯一性
    @RequestMapping("/queryByNickName")
    public ResponseBody queryByNickName(String nickName){
        return getResponseBody(searchUserService.searchByNickName(nickName));
    }



    @RequestMapping( value = "/addUser", method = RequestMethod.POST)
    public ResponseBody addUser(@RequestBody SearchUserDTO searchUserDTO){
        return getResponseBody(searchUserService.addUser(searchUserDTO)) ;
    }


    @RequestMapping( value = "/updateUser", method = RequestMethod.POST)
    public ResponseBody updateUser(@RequestBody SearchUserDTO searchUserDTO){
        return getResponseBody(searchUserService.synchronizeUser(searchUserDTO)) ;
    }





}
