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

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ForgetPassword), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText username = findViewById(R.id.signupusername);
        EditText email = findViewById(R.id.signupemail);
        EditText password = findViewById(R.id.signuppasswerd);
        EditText confirmpassword = findViewById(R.id.confirmpassword);
        Button reset = findViewById(R.id.resetbutton);
        DatabaseHelper database = new DatabaseHelper(this);

        reset.setOnClickListener(v -> {
            if(database.isEmailExists(email.getText().toString()) && database.isUsernameExists(username.getText().toString())){
                if(password.getText().toString().isEmpty() == false && password.getText().toString().equals(confirmpassword.getText().toString())){
                    database.updatePassword(username.getText().toString(),password.getText().toString());
                    Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "email or username not found", Toast.LENGTH_SHORT).show();
            }
        });

    }
}