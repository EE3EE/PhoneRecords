package com.zyx.android.phonerecords.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.android.phonerecords.R;
import com.zyx.android.phonerecords.Utils.RecyclerItemClickListener;
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
    private MediaPlayer mp;

    public static final int TODAY = 0;
    public static final int INCOMING = 1;
    public static final int OUTGOING = 2;

    protected RecyclerView recyclerView;


    /**
     * 播放录音，此处注意优化
     */
    private void playRecords(String savePath) {
        mp = new MediaPlayer();

        if (mp.isPlaying()) {
            mp.stop();
        }
        try {
            mp.setDataSource(savePath);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 在BaseFragment中加载RecyclerView对象
     * @param recyclerView
     */
    protected void loadingListView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
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
                    // 布局管理器设置显示方式：
                    // LinearLayoutManager类似ListView;
                    // GridLayoutManager类似GridView;
                    // StaggeredGridLayoutManager类似瀑布流
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new MyAdapter());
                    //添加点击事件
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(getContext(),recyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener(){

                                        @Override
                                        public void onItemClick(View view, int position) {

                                            Toast.makeText(getContext(), "播放" + infos.get(position).getSavePath(), Toast.LENGTH_SHORT).show();
                                            // 此处需优化
                                            playRecords(infos.get(position).getSavePath());
                                        }

                                        @Override
                                        public void onItemLongClick(View view, int position) {

                                        }
                                    }));
                    break;
                default:
                    break;
            }
        }
    };



    /**
     * RecycleView的适配器
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        /**
         * 自定义的ViewHolder
         */
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv_file_name;
            TextView tv_file_date;
            TextView tv_file_type;
            TextView tv_file_totaltime;
            /**
             * 通过构造方法，获得自定义的ViewHolder的对象
             * @param view 在创建自定义的MyViewHolder对象时，传入View参数
             */
            public MyViewHolder(View view){
                super(view);
                tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
                tv_file_date = (TextView) view.findViewById(R.id.tv_file_date);
                tv_file_type = (TextView) view.findViewById(R.id.tv_file_type);
                tv_file_totaltime = (TextView) view.findViewById(R.id.tv_file_totaltime);
            }

        }

        /**
         * 返回自定义的ViewHolder的对象
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //加载RecyclerView中每个View对应的布局文件，获得view
            View view = View.inflate(getContext(), R.layout.phone_records_item,null);
            //将view传入自定义的ViewHolder的构造方法，获得自定义的ViewHolder的对象
            MyViewHolder holder = new MyViewHolder(view);
            //返回自定义的ViewHolder的对象
            return holder;
        }

        /**
         * 对上一步获得的自定义的ViewHolder的对象进行赋值
         * （对应ListView中的getView，但省事很多）
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

            holder.tv_file_name.setText(infos.get(position).getName());
            holder.tv_file_date.setText(infos.get(position).getDate() + " " + infos.get(position).getTime());
            String type = (infos.get(position).getType() == 0) ? "呼入" : "呼出";
            holder.tv_file_type.setText(type);
            String totalTime = infos.get(position).getTotaltime();
            holder.tv_file_totaltime.setText("通话时长 " + totalTime);
        }

        /**
         * 对应ListView中的getCount方法
         * @return
         */
        @Override
        public int getItemCount() {
            return infos.size();
        }
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
