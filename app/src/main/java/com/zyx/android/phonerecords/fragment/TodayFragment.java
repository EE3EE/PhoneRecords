package com.zyx.android.phonerecords.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyx.android.phonerecords.R;


public class TodayFragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getContext(),R.layout.fragment_today,null);

        //指定Fragment对应的ListView
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_today_records);
        loadingListView(recyclerView);

        //获取参数对应的录音信息集合，并显示ListView
        fillData(TODAY);

        return view;
    }










}
