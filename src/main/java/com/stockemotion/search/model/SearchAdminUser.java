package com.stockemotion.search.model;

/**
 * Created by piguanghua on 7/27/17.
 */
import java.util.Date;
import javax.persistence.*;

@Table(name = "search_admin_user")
public class SearchAdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "sys_create_time")
    private Date sysCreateTime;

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
}
