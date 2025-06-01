package com.example.finalhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class mainpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainpage);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maintemplist), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Toolbar toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(this, profile.class);
            profileIntent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.action_activity_log) {
            Intent activityLogIntent = new Intent(this, ActivityLog.class);
            startActivity(activityLogIntent);
            return true;
        } else if (id == R.id.action_logout) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}