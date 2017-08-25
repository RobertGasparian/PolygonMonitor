package com.example.polygonmonitor.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.polygon_monitor.ModelsGeofenceInfo;
import com.example.polygonmonitor.activities.PolygonListActivity;
import com.example.poligonmonitor.R;


import java.util.List;

/**
 * Created by User on 8/11/2017.
 */

public class GeofenceListAdapter extends RecyclerView.Adapter<GeofenceListAdapter.GeoHolder>{

    private List<ModelsGeofenceInfo> itemList;
    private Context context;


    public GeofenceListAdapter(List<ModelsGeofenceInfo> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public GeoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.geo_item,parent,false);
        return new GeoHolder(view);
    }

    @Override
    public void onBindViewHolder(GeoHolder holder, int position) {

        final ModelsGeofenceInfo item = itemList.get(position);
        holder.latitude.setText(item.getLatitude());
        holder.longitude.setText(item.getLongitude());
        holder.radius.setText(item.getRadius());
        holder.id.setText(item.getId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PolygonListActivity.class);
                intent.putExtra(PolygonListActivity.GEO_ID,item.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void refreshData(List<ModelsGeofenceInfo> list) {
        itemList=list;
        notifyDataSetChanged();
    }

    class GeoHolder extends RecyclerView.ViewHolder{

        private TextView latitude, longitude, radius, id;
        private RelativeLayout relativeLayout;

        public GeoHolder(View itemView) {
            super(itemView);
            latitude = (TextView)itemView.findViewById(R.id.latitude);
            longitude = (TextView)itemView.findViewById(R.id.longitude);
            radius = (TextView)itemView.findViewById(R.id.radius);
            id = (TextView)itemView.findViewById(R.id.geo_id);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.geo_layout);

        }
    }
}