package com.stockemotion.search.controller;


import com.google.common.collect.Maps;
import com.stockemotion.common.http.ResponseBody;
import com.stockemotion.search.utils.SearchConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wode4 on 2016/10/18.
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    protected ResponseBody getResponseBody(String sid, Object data) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_OK));
        responseBody.setData(data);
        return responseBody;
    }

    protected ResponseBody getResponseBody(Object data) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_OK));
        responseBody.setTimestamp(System.currentTimeMillis());
        responseBody.setData(data);
        return responseBody;
    }

    protected ResponseBody getTimeStampResponseBody(String sid, Object data,Long timestamp) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_OK));
        responseBody.setData(data);
        responseBody.setTimestamp(timestamp);
        return responseBody;
    }

    protected ResponseBody getErrorResponse(String params,String msg) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_CLIENT_PARAMS_ERROR));
        Map data = Maps.newHashMap();
        data.put("error", msg);
        responseBody.setData(data);
        return responseBody;
    }

    protected ResponseBody getErrorResponse(String msg) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_CLIENT_ERROR));
        Map data = Maps.newHashMap();
        data.put("error", msg);
        responseBody.setData(data);
        return responseBody;
    }

    //aop 异常时用到
    public ResponseBody getErrorResponse(int sid, String msg){
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_SERVER_ERROR));
        Map data = Maps.newHashMap();
        data.put("sid", msg);
        data.put("error", msg);
        responseBody.setData(data);
        return responseBody;
    }

    public static ResponseBody getParamErrorResponse(){
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_CLIENT_ERROR));
        Map data = Maps.newHashMap();
        data.put("error", "param is error");
        responseBody.setData(data);
        return responseBody;
    }

    //aop 切割异常
    public static ResponseBody getNullParamResponse(){
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_CLIENT_PARAMS_ERROR));
        Map data = Maps.newHashMap();
        data.put("error", "param is null");
        responseBody.setData(data);
        return responseBody;
    }

    //aop 服务器异常
    public static ResponseBody getErrorServerResponse(){
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResult(String.valueOf(SearchConstant.STATUS_SERVER_ERROR));
        Map data = Maps.newHashMap();
        data.put("error", "Server is error");
        responseBody.setData(data);
        return responseBody;
    }






    protected boolean isExpired(){

        return true;
    }

   /* public boolean checkUser(Long uid, String udid){

        Boolean isUserForibdden = jedisDAO.getJedisReadTemplate().sismember(SearchConstant.FORBIDDEN_USER, ObjectUtils.toString(uid, ""));

        if (isUserForibdden != null && isUserForibdden) {

            LogUtils.logError("invalid user!");
            return false;
        }

        Boolean isDeviceForibdden = jedisDAO.getJedisReadTemplate().sismember(SearchConstant.FORBIDDEN_DEVICE, ObjectUtils.toString(udid, ""));

        if (isDeviceForibdden != null && isDeviceForibdden) {

            LogUtils.logError("invalid device!");
            return false;
        }

        String forbiddenLiveUser = jedisDAO.getJedisReadTemplate().get(SearchConstant.FORBIDDEN_LIVE_USER + ObjectUtils.toString(uid, ""));
        if (forbiddenLiveUser != null && !"".equals(forbiddenLiveUser)) {

            LogUtils.logError("forbidden live user!");
            return false;
        }

        return true;
    }*/
}
