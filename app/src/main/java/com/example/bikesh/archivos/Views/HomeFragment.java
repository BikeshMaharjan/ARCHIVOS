package com.example.bikesh.archivos.Views;


import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bikesh.archivos.Adapter.HomeListAdapter;
import com.example.bikesh.archivos.Class.CommonData;
import com.example.bikesh.archivos.Class.FTPConnection;
import com.example.bikesh.archivos.Class.ReadStoredPreference;
import com.example.bikesh.archivos.R;

import java.util.ArrayList;

/**
 * Created by bikesh on 1/4/17.
 */

public class HomeFragment extends Fragment {

    //Properties
    ListView listHome;



    NotificationManager mNotificationManager;

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

        mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        HomeListAdapter listAdapter = new HomeListAdapter(getActivity(), CommonData.mobileArray);
        listHome.setAdapter(listAdapter);

        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                CommonData.tappedList.add(value);

                ReadStoredPreference readStoredPreference = new ReadStoredPreference();
                readStoredPreference.readStoredPreferences(getActivity().getApplicationContext());
                String url = CommonData.DIRECTORY;

                for (String urlpart :
                        CommonData.tappedList) {
                    url += "/" + urlpart + "/" ;
                }

                listFilesfromDirectory(url);
            }
        });
    }

    public void listFilesfromDirectory(final String directory) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    FTPConnection ftpConnection = new FTPConnection(CommonData.IPADDRESS, CommonData.USERNAME, CommonData.PASSWORD);
                    CommonData.mobileArray = ftpConnection.listFiles(directory, getActivity().getApplicationContext(),mNotificationManager);
                    ftpConnection.disconnect();
                    getFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertDialog(getResources().getString(R.string.error),e.getLocalizedMessage());
                }
            }
        }).start();
    }

    public void showAlertDialog(final String title,final String message) {

        new Thread() {
            public void run() {
                getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle(title);
                        alertDialogBuilder.setMessage(message);
                        alertDialogBuilder.setCancelable(true);
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }
                });
            }
        }.start();
    }
}
