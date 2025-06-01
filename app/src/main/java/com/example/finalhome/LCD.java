package com.example.finalhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LCD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lcd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button back = findViewById(R.id.buttonlcd);
        back.setOnClickListener(v -> finish());
        Button display = findViewById(R.id.displayButton);
        display.setOnClickListener(v -> {
            // Write to fairbase
            EditText editText = findViewById(R.id.TextLCD);
            String value = editText.getText().toString();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/LCD");
            Toast.makeText(this, "here " + value, Toast.LENGTH_SHORT).show();
            myRef.setValue(value);
        });
    }
}