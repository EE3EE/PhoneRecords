package com.zyx.android.phonerecords.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zyx.android.phonerecords.R;


public class OutgoingFragment extends BaseFragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getContext(),R.layout.fragment_outgoing,null);

        //指定Fragment对应的ListView
        listView = (ListView) view.findViewById(R.id.lv_outgoing_records);
        loadingListView(listView);

        //获取参数对应的录音信息集合，并显示ListView
        fillData(OUTGOING);

        setOnItemClick();

        return view;
    }










}
