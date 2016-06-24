package com.dlkt.chang.mobileguard.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/24.
 * 联系人实体类
 */
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String phoneNum;

    /**
     * 显示数字拼音的首字母
     */
    private String sortLetters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
