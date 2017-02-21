package com.zyx.android.phonerecords.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.android.phonerecords.R;
import com.zyx.android.phonerecords.domin.PhoneRecordsInfo;
import com.zyx.android.phonerecords.engine.PhoneRecordsInfoProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ZYX on 2017/2/20.
 */

public class BaseFragment extends Fragment {

    private List<PhoneRecordsInfo> infos;
    private SharedPreferences sp;

    public static final int TODAY = 0;
    public static final int INCOMING = 1;
    public static final int OUTGOING = 2;

    protected ListView listView;

//    /**
//     * 查看录音播放进度的回调接口
//     */
//    public interface SeekBarCallBack{
//        /**
//         * 当前进度的回调方法
//         * @param progress
//         */
//        public void currentProgress(int progress);
//
//        /**
//         * 总进度的方法
//         * @param total
//         */
//        public void totalProgress(int total);
//
//    }


    /**
     * 播放录音
     * @param savePath 录音的绝对路径
     */
//    private void playRecords(String savePath,SeekBarCallBack back) {
    private void playRecords(String savePath) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(savePath);
//            int total = mp.getDuration();
//            back.totalProgress(total);
            mp.prepare();
            mp.start();
//            long time = System.currentTimeMillis();

            //此处可用Handler或Timer设置循环，while也行
//            while ((System.currentTimeMillis()-time)<total) {
//                back.currentProgress(mp.getCurrentPosition());
//                SystemClock.sleep(500);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }





    }




    /**
     * 在BaseFragment中加载ListView对象
     * @param listView
     */
    protected void loadingListView(ListView listView){
        this.listView = listView;
    }


    /**
     * 此处解决启动时无文件的Bug
     */
    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(getContext(), "没有录音文件！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    listView.setAdapter(new MyAdapter());
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置ListView的每一项的点击事件
     */
    public void setOnItemClick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "播放" + infos.get(position).getSavePath(), Toast.LENGTH_SHORT).show();

                playRecords(infos.get(position).getSavePath());
            }
        });
    }



    /**
     * ListView的适配器
     */
    protected class MyAdapter extends BaseAdapter {

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
                view = View.inflate(getContext(), R.layout.phone_records_item,null);
                holder.tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
                holder.tv_file_date = (TextView) view.findViewById(R.id.tv_file_date);
                holder.tv_file_type = (TextView) view.findViewById(R.id.tv_file_type);
                holder.tv_file_totaltime = (TextView) view.findViewById(R.id.tv_file_totaltime);
                view.setTag(holder);
            }

            holder.tv_file_name.setText(infos.get(position).getName());
            holder.tv_file_date.setText(infos.get(position).getDate() + " " + infos.get(position).getTime());

            String type = (infos.get(position).getType() == 0) ? "呼入" : "呼出";
            holder.tv_file_type.setText(type);

            String totalTime = infos.get(position).getTotaltime();
            holder.tv_file_totaltime.setText("通话时长 " + totalTime);

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


    /**
     * 获取Type对应的录音文件的信息的List集合，并使用Handler显示ListView的视图
     * @param type
     * ALL 全部录音文件
     * OUTGOING 呼出电话的录音文件信息
     * INCOMING 呼入电话的录音文件信息
     */
    protected void fillData(final int type) {
        new Thread(){
            @Override

            public void run() {

                sp = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                String path = sp.getString("path","");
                File dir = new File(path);

                //获取所有的录音信息
                File[] files = dir.listFiles();
                infos = PhoneRecordsInfoProvider.getPhoneRecordsInfos(files,type);
                if (!(infos.isEmpty())) {
                    //若List集合中存在信息
                    handler.sendEmptyMessage(1);
                }else{
                    //若List集合中不存在信息
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }



}
