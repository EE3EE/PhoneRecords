package com.zyx.android.phonerecords.engine;

import android.media.MediaPlayer;

import com.zyx.android.phonerecords.domin.PhoneRecordsInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public static List<PhoneRecordsInfo> getAllPhoneRecordsInfos(File[] files) {
        List<PhoneRecordsInfo> infos = new ArrayList<>();
        for (File file : files) {
            PhoneRecordsInfo info = new PhoneRecordsInfo();
            String filename = file.getName();
            String[] values = filename.split("_");

            info.setName(values[0]);
            info.setType(Integer.valueOf(values[1]));
            String date = values[2];
            String str = String.format("%s年%s月%s日%s:%s:%s",date.substring(0,4),
                    date.substring(4,6),date.substring(6,8),date.substring(8,10),
                    date.substring(10,12),date.substring(12,14));
            info.setDate(str);

            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            info.setTotaltime(mediaPlayer.getDuration());

            infos.add(info);
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


}
