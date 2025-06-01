package com.example.finalhome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profile extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainprofile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView image = findViewById(R.id.profileImage);
        EditText nametext = findViewById(R.id.signupname);
        EditText emailtext = findViewById(R.id.signupemail);
        String username = getIntent().getStringExtra("username");
        Log.d("ProfileActivity", "Username received: " + username);
        String[] userInfo = new DatabaseHelper(this).getUserInfo(username);
        String name = userInfo[0];
        String email = userInfo[1];
        nametext.setText(name);
        emailtext.setText(email);
        byte[] imageBytes = new DatabaseHelper(this).getImageBytes(username);
        if (imageBytes != null) {
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        }
        Button homepage = findViewById(R.id.homepage);
        homepage.setOnClickListener(v -> finish());
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent t = new Intent(profile.this, MainActivity.class);
            startActivity(t);
            finish();
        });
    }
}
