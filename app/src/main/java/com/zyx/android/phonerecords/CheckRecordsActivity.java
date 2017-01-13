package com.zyx.android.phonerecords;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;


/**
 * 作为录音管理页面使用，暂时并未完成
 */
public class CheckRecordsActivity extends Activity {

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PhoneRecords/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);

        createSaveRecordsDir();



    }


    /**
     * 创建保存录音文件的文件夹
     */
    private void createSaveRecordsDir() {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }



}
