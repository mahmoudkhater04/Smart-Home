package com.example.finalhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Light extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_light);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button back = findViewById(R.id.buttonLight);
        back.setOnClickListener(v -> finish());
        Button button = findViewById(R.id.OnOffButton);
        button.setOnClickListener(v -> {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/LED");
            TextView txt = findViewById(R.id.textlight);
            if(txt.getText().toString().equals("on")) {
                myRef.setValue("false");
                txt.setText("off");
            }else{
                myRef.setValue("true");
                txt.setText("on");
            }
        });
    }
}