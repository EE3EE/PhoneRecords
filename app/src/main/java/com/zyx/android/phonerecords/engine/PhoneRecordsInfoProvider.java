package com.zyx.android.phonerecords.engine;

import com.zyx.android.phonerecords.domin.PhoneRecordsInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZYX on 2017/1/13.
 */

public class PhoneRecordsInfoProvider {

    /**
     * 获得所有的通话记录
     * @param dir 传入保存录音文件的文件夹
     * @return
     */
    public static List<PhoneRecordsInfo> getAllPhoneRecordsInfos(File dir) {
        List<PhoneRecordsInfo> infos = new ArrayList<PhoneRecordsInfo>();
        File[] files = dir.listFiles();
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

            infos.add(info);
        }

        return infos;
    }

}
