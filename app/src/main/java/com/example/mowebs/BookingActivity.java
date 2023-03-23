package com.example.mowebs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Button button = findViewById(R.id.btn_payment);

        // tempelkan fungsi klik untuk button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menggunakan intent untuk berpindah
                Intent intent = new Intent(BookingActivity.this, RatingActivity.class);
                // memulai activity baru sesuai yang diminta pada intent di atas
                startActivity(intent);
            }
        });
    }
}