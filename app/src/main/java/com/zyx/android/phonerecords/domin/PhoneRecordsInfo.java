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


}
