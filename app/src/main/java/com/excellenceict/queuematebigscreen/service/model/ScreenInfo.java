package com.excellenceict.queuematebigscreen.service.model;

public class ScreenInfo {
    private String screenId;
    private String screenCode;
    private String screenName;

    public ScreenInfo(String screenId, String screenCode, String screenName) {
        this.screenId = screenId;
        this.screenCode = screenCode;
        this.screenName = screenName;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return  screenName ;
    }
}
