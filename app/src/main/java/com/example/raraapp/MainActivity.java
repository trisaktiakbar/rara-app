package com.example.raraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView btn_cuaca_realtime, btn_riwayat, btn_analisis, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cuaca_realtime = findViewById(R.id.btn_card_cuaca_realtime);
        btn_cuaca_realtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CuacaRealtimeActivity.class);
                startActivity(i);
            }
        });

        btn_riwayat = findViewById(R.id.btn_card_riwayat);
        btn_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MenuRiwayatActivity.class);
                startActivity(i);
            }
        });


        btn_analisis = findViewById(R.id.btn_card_analisis);
        btn_analisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AnalisisActivity.class);
                startActivity(i);
            }
        });

        btn_logout = findViewById(R.id.btn_card_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                System.exit(0);
            }
        });

    }
}