package com.zyx.android.phonerecords.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


/**
 * 此页面的关键在于保证服务在通话过程中一直存活。
 * 注意测试
 */
public class RecorderService extends Service {

    private SharedPreferences sp;
    private MediaRecorder mediaRecorder;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("config",MODE_PRIVATE);
        String filename = sp.getString("filename","");

        if ((!TextUtils.isEmpty(filename))){
            //当文件名存在，开始录音

            //注意：这个API使用时要小心，尤其是声音源和路径这两块
            // 1.实例化一个录音机
            mediaRecorder = new MediaRecorder();
            //2.指定录音机的声音源
            //此处的MediaRecorder.AudioSource.VOICE_CALL使用时注意，有些机型不可用，造成mediaRecorder.start()失败
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);//模拟器不可用
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//华为不可用
            //3.设置录制的文件输出的格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //4.设置文件路径
            //若SD卡上的文件夹和文件不存在，必须先创建好（文件夹在一开始就在CheckRecordsActivity中创建）,否则就会报错
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PhoneRecords/" + filename + ".3gp";

            File file = new File(path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "录音文件创建失败", Toast.LENGTH_SHORT).show();
            }

            mediaRecorder.setOutputFile(path);
            //5.设置音频的编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                //6.准备开始录音
                mediaRecorder.prepare();
                //7.开始录音
                mediaRecorder.start();
                Toast.makeText(this, "录音开始", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "通话录音启动失败，请尝试修改声源或录音文件的保存路径", Toast.LENGTH_SHORT).show();
            }

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mediaRecorder!=null) {
            sp = getSharedPreferences("config",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("filename","");
            editor.commit();

            try {
                //8.停止捕获
                mediaRecorder.stop();
                Toast.makeText(this, "录音已保存", Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Toast.makeText(this, "通话录音出错，请确认是否录制成功", Toast.LENGTH_SHORT).show();
            }
            //9.释放资源
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


}
