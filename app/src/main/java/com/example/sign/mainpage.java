package com.example.sign;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class mainpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ArrayList<itemlist> MainList = new ArrayList<>();

        MainList.add(new itemlist("Temperature",R.drawable.temperature));
        MainList.add(new itemlist("Ultrasonic Sensor",R.drawable.ultrasonic));
        MainList.add(new itemlist("Light",R.drawable.light));
        MainList.add(new itemlist("Display LCD",R.drawable.lcd));
        MainList.add(new itemlist("Smoke Sensor",R.drawable.smoke));

        ListView listView = findViewById(R.id.listview);
        CustomAdapter adapter = new CustomAdapter(this, MainList);
        listView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchview);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
}