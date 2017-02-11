package com.zyx.android.phonerecords.domin;

/**
 * Created by ZYX on 2017/1/13.
 */

public class PhoneRecordsInfo {

    private String name;
    private String date;
    /**
     * 录音类型，0为呼入，1为呼出
     */
    private int type;
    /**
     * 每个录音文件的时长
     */
    private double totaltime;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(double totaltime) {
        this.totaltime = totaltime;
    }
}
