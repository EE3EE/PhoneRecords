package com.zyx.android.phonerecords;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.android.phonerecords.domin.PhoneRecordsInfo;
import com.zyx.android.phonerecords.engine.PhoneRecordsInfoProvider;

import java.io.File;
import java.util.List;


/**
 * 作为录音管理页面使用，暂未完成
 */
public class ManageRecordsActivity extends Activity {

    private ListView iv_phone_records;
    private File dir;
    List<PhoneRecordsInfo> infos;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    iv_phone_records.setAdapter(new MyAdapter());
                    break;
                case 1:
                    Toast.makeText(ManageRecordsActivity.this, "没有录音文件！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PhoneRecords/";
        createSaveRecordsDir(path);

        iv_phone_records = (ListView) findViewById(R.id.iv_phone_records);

        fillData();
    }


    /**
     * 获取所有录音文件的信息
     */
    private void fillData() {
        new Thread(){
            @Override
            public void run() {
                //获取所有的录音信息
                File[] files = dir.listFiles();
                if (!(files == null)) {
                    infos = PhoneRecordsInfoProvider.getAllPhoneRecordsInfos(files);
                    handler.sendEmptyMessage(0);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
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
     * ListView的适配器
     */
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView != null){
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }else{
                holder = new ViewHolder();
                view = View.inflate(ManageRecordsActivity.this,R.layout.phone_records_item,null);
                holder.tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
                holder.tv_file_date = (TextView) view.findViewById(R.id.tv_file_date);
                holder.tv_file_type = (TextView) view.findViewById(R.id.tv_file_type);
                holder.tv_file_totaltime = (TextView) view.findViewById(R.id.tv_file_totaltime);
                view.setTag(holder);
            }

            holder.tv_file_name.setText(infos.get(position).getName());
            holder.tv_file_date.setText(infos.get(position).getDate());

            String type = (infos.get(position).getType() == 0) ? "呼入" : "呼出";
            holder.tv_file_type.setText(type);

            String totalTime = String.valueOf((int) infos.get(position).getTotaltime()/1000);
            holder.tv_file_totaltime.setText(totalTime+"秒");

            return view;
        }
    }


    /**
     * View容器
     */
    static class ViewHolder{
        TextView tv_file_name;
        TextView tv_file_date;
        TextView tv_file_type;
        TextView tv_file_totaltime;
    }

}
