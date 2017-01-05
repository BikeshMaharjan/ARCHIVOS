package com.example.bikesh.archivos.Views;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bikesh.archivos.Adapter.HomeListAdapter;
import com.example.bikesh.archivos.Class.CommonData;
import com.example.bikesh.archivos.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bikesh on 1/4/17.
 */

public class HomeFragment extends Fragment {

    //Properties
    ListView listHome;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_home_list,container,false);
        fragment.setBackgroundColor(Color.WHITE);
        listHome = (ListView) fragment.findViewById(R.id.home_list);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        HomeListAdapter listAdapter = new HomeListAdapter(getActivity(), CommonData.mobileArray);
        listHome.setAdapter(listAdapter);
    }
}
