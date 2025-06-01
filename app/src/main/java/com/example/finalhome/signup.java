package com.example.finalhome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private Uri selectedImageUri;
    private byte[] imageBytes;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maintemplist), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        EditText editName = findViewById(R.id.signupname);
        EditText editUserName = findViewById(R.id.signupusername);
        EditText editEmail = findViewById(R.id.signupemail);
        EditText editPassword = findViewById(R.id.signuppasswerd);
        Button signupbutton = findViewById(R.id.signupbutton);
        Button buttonImage = findViewById(R.id.signupbuttonimage);
        DatabaseHelper database = new DatabaseHelper(this);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("users");

        Log.d(TAG, "signup activity created");

        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            imageBytes = convertImageToBytes(selectedImageUri);

                            Toast.makeText(this, "Image selected.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Image selected: " + selectedImageUri.toString());
                        } else {
                            Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                            imageBytes = null;
                            Log.d(TAG, "No image selected");
                        }
                    } else {
                        Log.d(TAG, "Image selection canceled or failed");
                    }
                }
        );

        buttonImage.setOnClickListener(v -> {
            Log.d(TAG, "Image selection button clicked");
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            imagePickerLauncher.launch(i);
        });

        signupbutton.setOnClickListener(v -> {
            String username = editUserName.getText().toString().trim();
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            Log.d(TAG, "Signup button clicked with username: " + username + ", email: " + email);

            if (imageBytes == null || name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Signup failed: Missing fields or image");
                return;
            }

            if (database.isUsernameExists(username)) {
                Toast.makeText(this, "Username already exists. Please choose another.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Signup failed: Username exists - " + username);
                return;
            }

            if (database.isEmailExists(email)) {
                Toast.makeText(this, "Email already exists. Please use another.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Signup failed: Email exists - " + email);
                return;
            }

            boolean isAdded = database.addUser(name, username, email, password, imageBytes);
            if (isAdded) {
                firebaseDatabase.child(username).child("username").setValue(username);
                firebaseDatabase.child(username).child("name").setValue(name);
                firebaseDatabase.child(username).child("password").setValue(password);
                firebaseDatabase.child(username).child("email").setValue(email);
                firebaseDatabase.child(username).child("imageBytes").setValue(Arrays.toString(imageBytes));

                Toast.makeText(signup.this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Signup successful for user: " + username);
                Intent intent1 = new Intent(signup.this, MainActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(signup.this, "Sign-Up FAILED! Please try again.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Signup failed during addUser for user: " + username);
            }
        });
    }

    private byte[] convertImageToBytes(Uri imageUri) {
        if (imageUri == null) {
            Log.d(TAG, "convertImageToBytes called with null imageUri");
            return null;
        }
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            Log.d(TAG, "Image converted to bytes successfully");
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to convert image.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error converting image to bytes: " + e.getMessage());
            return null;
        }
    }
}