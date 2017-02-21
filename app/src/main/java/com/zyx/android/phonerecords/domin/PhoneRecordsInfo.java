package com.zyx.android.phonerecords.domin;

/**
 * Created by ZYX on 2017/1/13.
 */

public class PhoneRecordsInfo {

    private String name;
    private String date;
    private String time;
    /**
     * 录音类型，0为呼入，1为呼出
     */
    private int type;
    /**
     * 每个录音文件的时长
     */
    private String totaltime;
    /**
     * 录音文件的路径，播放时调用
     */
    private String savePath;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
