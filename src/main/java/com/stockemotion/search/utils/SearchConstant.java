package com.stockemotion.search.utils;

import com.stockemotion.common.utils.PropertiesGetter;

/**
 * Created by pigaunghua on 2016/12/6.
 */
public interface SearchConstant {
    int STATUS_SERVER_ERROR = 500;

    int STATUS_CLIENT_ERROR = 601;

    int STATUS_CLIENT_PARAMS_ERROR = 602;

    int STATUS_CLIENT_TOKEN_ERROR = 603;

    int STATUS_CLIENT_MENTION_MUCH_ERROR = 604;

    int STATUS_OK = 200;

    int STATUS_UNAUTHORIZED = 401;

    int LOGIN_TYPE_FACEBOOK = 1;

    int LOGIN_TYPE_TWITTER = 2;

    int LOGIN_TYPE_WEIBO = 4;

    int LOGIN_TYPE_QQ = 4;

    int LOGIN_TYPE_WEIXIN = 6;

    int LOGIN_TYPE_SYSTEM = 3;

    //错误码
    String OTHER_ERROR_CODE = "E100";  //未知错误
    String SERVER_ERROR_CODE = "E500"; //服务器异常错误

    String PARAMS_ERROR_CODE = "E008"; //参数不合法
    String USER_INVALID_ERROR_CODE = "E009"; //用户不存在或被禁止

    String LOGIN_ERROR_CODE = "E011"; //登陆失败
    String WEIBO_ERROR_CODE = "E012"; //获取微博好友失败
    String SHARE_ERROR_CODE = "E013"; //分享失败
    String GET_TOKEN_ERROR_CODE = "E014"; //获取上传token失败
    String COMPRESS_PIC_ERROR_CODE = "E015"; //压缩图片失败
    String MESSAGE_ERROR_CODE = "E017"; //获取个人消息失败

    String NICKNAME_DUPLICATE_ERROR_CODE = "E019"; // 昵称更新失败昵称重复
    String SAVE_PIC_ERROR_CODE = "E020"; //保存图片失败
    String OUTOF_AUTHENTICATION_MESSAGE_LIMIT = "E021"; //超出每日短信限制条数
    String ERROR_MESSAGECODE = "E022"; //验证码不对
    String ERROR_NO_MESSAGECODE = "E023"; //未填写验证码
    String ERROR_PHONENO_REPEAT = "E024"; //改手机号已被注册
    String ERROR_MESSAGECODE_NOTINCACHE = "E025"; //验证码失效
    String ERROR_MESSAGECODE_TOOFREQUENTLY = "E026"; //操作过于频繁，请2分钟后重试



    String SOCIAL_CHECK_NICKNAME = PropertiesGetter.getValue("social_url_checkNickName");
}
