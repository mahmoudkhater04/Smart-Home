package com.example.finalhome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflator;
    private List<itemlist> original;
    private List<itemlist> filtered;
    private Context mContext;

    public CustomAdapter(@NonNull Context context, List<itemlist> items) {
        mContext = context;
        inflator = LayoutInflater.from(context);
        original = new ArrayList<>();
        original.addAll(items);
        filtered = new ArrayList<>();
        filtered.addAll(items);
    }

    @Override
    public int getCount() {
        return filtered.size();
    }

    @Override
    public itemlist getItem(int position) {
        return filtered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        itemlist current = getItem(pos);

        if(convertView == null){
            convertView = inflator.inflate(R.layout.custom_list_view,null);
            convertView.setTag(current);
        }

        ImageView img = convertView.findViewById(R.id.imagelist);
        TextView txt = convertView.findViewById(R.id.textlist);

        img.setImageResource(current.getImgID());
        txt.setText(current.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(current.getName().equals("Temperature")){
                    intent = new Intent(mContext, Temperature.class);
                    mContext.startActivity(intent);
                }else if(current.getName().equals("Ultrasonic Sensor")){
                    intent = new Intent(mContext, Ultrasonic.class);
                    mContext.startActivity(intent);
                }else if(current.getName().equals("Light")){
                    intent = new Intent(mContext, Light.class);
                    mContext.startActivity(intent);
                }else if(current.getName().equals("Display LCD")){
                    intent = new Intent(mContext, LCD.class);
                    mContext.startActivity(intent);
                }else{
                    intent = new Intent(mContext, Smoke.class);
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    public void filter(String query){
        query = query.toLowerCase();
        filtered.clear();
        if(query.isEmpty()){
            filtered.addAll(original);
        }else {
            for (itemlist item : original) {
                if (item.getName().toLowerCase().contains(query)) {
                    filtered.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
