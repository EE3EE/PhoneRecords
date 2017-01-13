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
            String[] date = values[2].split("\\.");
            info.setDate(date[0]);

            infos.add(info);
        }

        return infos;
    }

}
