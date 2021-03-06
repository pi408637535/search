package com.stockemotion.search.model;

import com.stockemotion.common.utils.JsonUtils;
import com.stockemotion.search.dto.innner.SearchUserDTO;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import javax.persistence.*;

@Table(name = "search_user")
public class SearchUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nick_name")
    @Field(searchAnalyzer="ik", analyzer="ik", store=true, index = FieldIndex.not_analyzed, type= FieldType.String)
    private String nickName;

    @Field(searchAnalyzer="ik", analyzer="ik", store=true, index = FieldIndex.not_analyzed, type= FieldType.String)
    private String cellphone;

    @Field(searchAnalyzer="ik", analyzer="ik" ,type= FieldType.String)
    private String introduce;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "sys_create_time")
    private Date sysCreateTime;

    @Column(name = "sys_update_time")
    private Date sysUpdateTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * @return cellphone
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * @param cellphone
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    /**
     * @return introduce
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    /**
     * @return picture_url
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    /**
     * @return sys_create_time
     */
    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    /**
     * @param sysCreateTime
     */
    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    /**
     * @return sys_update_time
     */
    public Date getSysUpdateTime() {
        return sysUpdateTime;
    }

    /**
     * @param sysUpdateTime
     */
    public void setSysUpdateTime(Date sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }


    public static SearchUser SearchUserDTO2SearchUser(SearchUserDTO socialUser) {
        String jsonString = JsonUtils.TO_JSON(socialUser);
        SearchUser searchUser = (SearchUser)JsonUtils.TO_OBJ(jsonString, SearchUser.class);
        return  searchUser;
    }
}