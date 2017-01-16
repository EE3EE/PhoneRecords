package com.zyx.android.phonerecords;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
     * 向ListView中填充数据
     */
    private void fillData() {
        if (infos == null) {
            //获取所有的录音信息
            infos = PhoneRecordsInfoProvider.getAllPhoneRecordsInfos(dir);
        }
        iv_phone_records.setAdapter(new MyAdapter());
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
                view.setTag(holder);
            }

            holder.tv_file_name.setText(infos.get(position).getName());
            holder.tv_file_date.setText(infos.get(position).getDate());
            String type = (infos.get(position).getType() == 0) ? "呼入" : "呼出";
            holder.tv_file_type.setText(type);

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
    }

}
