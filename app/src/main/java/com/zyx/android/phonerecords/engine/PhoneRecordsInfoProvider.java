package com.zyx.android.phonerecords.engine;

import android.media.MediaPlayer;

import com.zyx.android.phonerecords.domin.PhoneRecordsInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZYX on 2017/1/13.
 */

public class PhoneRecordsInfoProvider {

    /**
     * 获得所有的通话记录(通话记录已按录音的时间进行排序)
     * @param files 传入保存的录音文件的文件的数组
     * @return
     * 返回一个含有所有录音信息的List集合
     */

    /**
     * 获得通话录音文件信息的集合(通话记录已按录音的时间进行排序)
     * @param files 文件的数组
     * @param type 要返回的录音文件的类型
     * @return 返回一个含有相应Type的录音信息的List集合
     */
    public static List<PhoneRecordsInfo> getPhoneRecordsInfos(File[] files,int type) {

        String today = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        List<PhoneRecordsInfo> infos = new ArrayList<>();
        for (File file : files) {
            PhoneRecordsInfo info = new PhoneRecordsInfo();
            String filename = file.getName();
            String[] values = filename.split("_");

            info.setSavePath(file.getPath());

            info.setName(values[0]);
            info.setType(Integer.valueOf(values[1]));

            String date_time = values[2];
            String date = String.format("%s/%s/%s",date_time.substring(0,4),
                    date_time.substring(4,6),date_time.substring(6,8));
            String time = String.format("%s:%s:%s",date_time.substring(8,10),
                    date_time.substring(10,12),date_time.substring(12,14));
            info.setDate(date);
            info.setTime(time);


            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int seconds = mediaPlayer.getDuration()/1000;
            String totaltime = secToTime(seconds);

            info.setTotaltime(totaltime);

            //将符合相应Type的信息添加入相应的集合
            switch (type){
                case 0://获得今天的电话的集合
                    if(date.replace("/","").equals(today)) {
                        infos.add(info);
                    }
                    break;
                case 1://获得呼入电话录音的集合
                    if (Integer.valueOf(values[1]) == 0){
                        infos.add(info);
                    }
                    break;
                case 2://获得呼出电话录音的集合
                    if (Integer.valueOf(values[1]) == 1){
                        infos.add(info);
                    }
                    break;
                default:
                    break;
            }

        }

        //对录音文件的信息按照录音时间进行排序
        Collections.sort(infos, new Comparator<PhoneRecordsInfo>() {
            @Override
            public int compare(PhoneRecordsInfo o1, PhoneRecordsInfo o2) {

                String date1 = o1.getDate();
                String date2 = o2.getDate();
                return -date1.compareTo(date2);
            }
        });

        return infos;
    }


    /**
     * 将秒数转化为"时:分：秒"的形式
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }


    /**
     * 将数字格式化，不够10的前面补0
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }



}
