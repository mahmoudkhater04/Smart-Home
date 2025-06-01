package com.example.finalhome;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ultrasonic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ultrasonic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button back = findViewById(R.id.buttonUltra);
        back.setOnClickListener(v -> finish());
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/Ultrasonic Sensor/Distance");
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotificationPermission")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                TextView textView = findViewById(R.id.TempView);
                textView.setText(value);
                int distance = Integer.parseInt(dataSnapshot.getValue(String.class));
                if (distance < 40) {
                    String channelId = "alert_channel";
                    String channelName = "Alert Notifications";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                channelName,
                                NotificationManager.IMPORTANCE_HIGH
                        );
                        channel.setDescription("Notifications for distance alerts");
                        notificationManager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                            .setSmallIcon(R.drawable.baseline_circle_notifications_24) // Ensure this icon exists
                            .setContentTitle("Alert")
                            .setContentText("We are at Risk!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
                    Toast.makeText(Ultrasonic.this, "We are at Risk!", Toast.LENGTH_SHORT).show();
                    notificationManager.notify(0, mBuilder.build());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}