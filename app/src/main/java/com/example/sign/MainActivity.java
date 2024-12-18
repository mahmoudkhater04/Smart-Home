package com.example.sign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText username1;
    EditText password1;
    Button loginbutton;
    private DatabaseHelper databaseHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maintemplist), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.signuppasswerd);
        loginbutton = findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(v -> {

            String user = username1.getText().toString().trim();
            String pass = password1.getText().toString().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
            else {
                boolean isValid = databaseHelper.isMatch(user, pass);
                if (isValid) {
                    Intent intent1 = new Intent(MainActivity.this, mainpage.class);
                    intent1.putExtra("username", user);
                    startActivity(intent1);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signup = findViewById(R.id.singupbtn);
        signup.setOnClickListener(v -> {
            Intent t = new Intent(MainActivity.this, com.example.sign.signup.class);
            startActivity(t);
        });
        Button forget = findViewById(R.id.forgetPassword);
        forget.setOnClickListener(v -> {
            Intent t = new Intent(MainActivity.this, com.example.sign.ForgetPassword.class);
            startActivity(t);
        });
    }
}

