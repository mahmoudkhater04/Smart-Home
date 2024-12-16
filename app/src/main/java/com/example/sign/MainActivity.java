package com.example.sign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText username1;
    EditText password1 ;
    Button loginbutton;
    private DatabaseHelper databaseHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(this);
//        EditText username = (EditText) findViewById(R.id.username);
//        MaterialButton button = (MaterialButton) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, login.class);
//                startActivity(intent);
//            }
//        });
        username1 = (EditText) findViewById(R.id.username1);
        password1 = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username1.getText().toString().trim();
                String pass = password1.getText().toString().trim();
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isValid = databaseHelper.ismatch(user, pass);
                    if (isValid) {

//                        Intent intent = new Intent(MainActivity.this, login.class);
//                        startActivity(intent);
//                        finish();
                        Intent intent1 = new Intent(MainActivity.this, mainpage.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }


//
            }

        });

        Button signup = (Button) findViewById(R.id.singupbtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, login.class);
                startActivity(t);
            }
        });
    }
}

