package com.example.polygonmonitor.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.example.poligonmonitor.R;
import com.example.polygon_monitor.PolygonMonitorController;
import com.example.polygonmonitor.adapters.PolygonListAdapter;


public class PolygonListActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_list);
        TextView geoId = (TextView) findViewById(R.id.polygon_geo_id);
        RecyclerView polygonRv = (RecyclerView) findViewById(R.id.polygon_rv);
        Intent intent = getIntent();
        geoId.setText(intent.getStringExtra(PolygonMonitorController.GEO_ID));
        PolygonMonitorController controller = PolygonMonitorController.getInstance();
        PolygonListAdapter adapter = new PolygonListAdapter(this, controller.getPolygon(this,intent.getStringExtra(PolygonMonitorController.GEO_ID)));
        polygonRv.setLayoutManager(new LinearLayoutManager(this));
        polygonRv.setAdapter(adapter);

    }
}