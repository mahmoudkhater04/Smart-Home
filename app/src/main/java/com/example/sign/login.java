package com.example.sign;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class login extends AppCompatActivity {
    private Uri selectedImageUri;
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize views
        EditText editname = findViewById(R.id.name);
        EditText editemail = findViewById(R.id.email);
        EditText editpassword = findViewById(R.id.password);
        Button signupbutton = findViewById(R.id.button);
        Button buttonimage = findViewById(R.id.buttonimage);



        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up button click listener
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editname.getText().toString().trim();
                String email = editemail.getText().toString().trim();
                String password = editpassword.getText().toString().trim();
                String confirmpassword = signupbutton.getText().toString().trim();

                if (!password.equals(confirmpassword)) {
                    Toast.makeText(login.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }

                // Validate inputs
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Add user to database
                    boolean isInserted = databaseHelper.addUser(name, email, password);
                    if (isInserted) {
                        Toast.makeText(login.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity or redirect to login
                    } else {
                        Toast.makeText(login.this, "Sign-up failed. Email might already exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Set up the activity result launcher for selecting an image
//        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        selectedImageUri = result.getData().getData();
//                        if (selectedImageUri != null) {
//                            Toast.makeText(this, "Image selected: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//
//
//        buttonimage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                i.setType("image/*");
//                imagePickerLauncher.launch(i);
//            }
//        });




        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();


            // Here, you would upload the image to your database.
            // You can use libraries like Retrofit or Volley to make network requests.
            // First, convert the image to a byte array:
            Bitmap bitmap = BitmapFactory.decodeFile(getPath(selectedImageUri));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();

            // Then, send the byte array to your server-side script to store it in the database.
            // You can use HTTP POST requests with appropriate parameters to send the image data.
        }
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return null;
        }
    }



    }


