package com.dlkt.chang.mobileguard.domain;

/**
 * 版本的Url实体类
 * Created by Administrator on 2016/6/15.
 */
public class UrlBean {
    private String url;//apk下载路径
    private int versionCode;//版本号
    private String versionName;//版本的名称
    private String desc;//版本的详细信息

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "UrlBean [url=" + url + ", versionCode=" + versionCode
                + ", desc=" + desc + "]";
    }
}
