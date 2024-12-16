package com.example.sign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        ImageView img = convertView.findViewById(R.id.image);
        TextView txt = convertView.findViewById(R.id.text);

        img.setImageResource(current.getImgID());
        txt.setText(current.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
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
