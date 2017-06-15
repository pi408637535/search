package com.stockemotion.search.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "search_auto_id")
public class SearchAutoId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auto_id")
    private Long autoId;

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
     * @return auto_id
     */
    public Long getAutoId() {
        return autoId;
    }

    /**
     * @param autoId
     */
    public void setAutoId(Long autoId) {
        this.autoId = autoId;
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
}