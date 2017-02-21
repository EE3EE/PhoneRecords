package com.zyx.android.phonerecords;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.zyx.android.phonerecords.fragment.IncomingFragment;
import com.zyx.android.phonerecords.fragment.OutgoingFragment;
import com.zyx.android.phonerecords.fragment.TodayFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 作为录音管理页面使用，暂未完成
 */
public class ManageRecordsActivity extends FragmentActivity{


    @BindView(R.id.btn_today)
    Button btnToday;
    @BindView(R.id.btn_outgoing)
    Button btnOutgoing;
    @BindView(R.id.btn_incoming)
    Button btnIncoming;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private File dir;
    private SharedPreferences sp;
    private Fragment outgoingFragment;
    private Fragment todayFragment;
    private Fragment incomingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);
        ButterKnife.bind(this);


        //获取并创建录音保存路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PhoneRecords/";
        createSaveRecordsDir(path);

        //将"录音保存路径"保存到配置文件
        sp = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("path", path);
        editor.commit();

        //使用FragmentManager动态加载Fragment
        todayFragment = new TodayFragment();
        outgoingFragment = new OutgoingFragment();
        incomingFragment = new IncomingFragment();
        fragmentManager.beginTransaction().add(R.id.fl_content, incomingFragment).commit();

    }


    /**
     * 创建保存录音文件的文件夹
     */
    private void createSaveRecordsDir(String path) {
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    /**
     * 此处注意调试
     * @param view
     */
    @OnClick({R.id.btn_today, R.id.btn_outgoing, R.id.btn_incoming})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_today:
                fragmentManager.beginTransaction()
//                        .hide(inComingFragment)
//                        .hide(outGoingFragment)
                        .replace(R.id.fl_content, todayFragment)
                        .commit();
                break;
            case R.id.btn_outgoing:
                fragmentManager.beginTransaction()
//                        .hide(todayFragment)
//                        .hide(inComingFragment)
                        .replace(R.id.fl_content, outgoingFragment)
                        .commit();
                break;
            case R.id.btn_incoming:
                fragmentManager.beginTransaction()
//                        .hide(todayFragment)
//                        .hide(outGoingFragment)
                        .replace(R.id.fl_content, incomingFragment)
                        .commit();
                break;
        }
    }



}
