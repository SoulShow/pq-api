package com.pq.api.dto;

/**
 * @author liutao
 */
public class ReleaseVersionDto {

    private Boolean enforceFlag;

    private int versionCode;

    private String versionNo;

    private String downUrl;

    private Double tarSize;

    private String versionInfo;

    public Boolean getEnforceFlag() {
        return enforceFlag;
    }

    public void setEnforceFlag(Boolean enforceFlag) {
        this.enforceFlag = enforceFlag;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public Double getTarSize() {
        return tarSize;
    }

    public void setTarSize(Double tarSize) {
        this.tarSize = tarSize;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}