package com.example.bikesh.archivos.Views;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bikesh.archivos.Class.CommonData;
import com.example.bikesh.archivos.Class.FTPConnection;
import com.example.bikesh.archivos.Class.NotificationGenerate;
import com.example.bikesh.archivos.Class.ReadStoredPreference;
import com.example.bikesh.archivos.R;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Synchronizing with server...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                                ReadStoredPreference readStoredPreference = new ReadStoredPreference();
                                readStoredPreference.readStoredPreferences(getApplicationContext());
                             listFilesfromDirectory(CommonData.DIRECTORY);
                         }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Settings()).commit();
        } else if(id == R.id.nav_Home) {

            getFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment(), null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //User defined functions

    public void listFilesfromDirectory(final String directory) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FTPConnection ftpConnection = new FTPConnection(CommonData.IPADDRESS, CommonData.USERNAME, CommonData.PASSWORD);
                    CommonData.mobileArray = ftpConnection.listFiles(directory);
                    ftpConnection.disconnect();

                    getFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationGenerate notify = new NotificationGenerate();
                    notify.generateNotification(getApplicationContext(), mNotificationManager );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
