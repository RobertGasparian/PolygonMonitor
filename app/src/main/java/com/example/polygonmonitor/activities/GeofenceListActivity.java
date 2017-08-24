package com.example.polygonmonitor.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.polygon_monitor.helpers.DBHelper;
import com.example.polygon_monitor.models.GeofenceInfo;
import com.example.polygonmonitor.adapters.GeofenceListAdapter;
import com.example.poligonmonitor.R;


import java.util.List;

public class GeofenceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<GeofenceInfo> geoList;
    private GeofenceListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private DBHelper dbHelper;


    private final int LOCATION_ACCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_ACCESS);
                return;
            } else {
                init();
            }

        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    adapter.refreshData(dbHelper.getAllGeofences());
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.geo_rv);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_main);
        dbHelper = new DBHelper(this);
        geoList = dbHelper.getAllGeofences();
        adapter = new GeofenceListAdapter(geoList, GeofenceListActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case LOCATION_ACCESS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    init();

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.access_denied)
                            .setMessage(R.string.dialog_message)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GeofenceListActivity.this.finish();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
        }
    }


}