package com.stockemotion.search.controller;

import com.stockemotion.search.mapper.SearchAutoIdMapper;
import com.stockemotion.search.model.SearchAutoId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by piguanghua on 6/15/17.
 */
@RestController
@RequestMapping("/social")
public class SocialUserController {

    @Resource
    SearchAutoIdMapper searchAutoIdMapper;

    @RequestMapping("/get")
    public String get(){
        SearchAutoId searchAutoId = searchAutoIdMapper.selectAll().get(0);

        return "get";
    }
}
