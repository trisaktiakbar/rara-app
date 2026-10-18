package com.example.raraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuRiwayatActivity extends AppCompatActivity {

    CardView btn_riwayat_suhu, btn_riwayat_kelembaban_udara, btn_riwayat_hujan, btn_riwayat_cahaya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_riwayat);

        btn_riwayat_suhu = findViewById(R.id.btn_card_riwayat_suhu);
        btn_riwayat_suhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuRiwayatActivity.this, RiwayatSuhuActivity.class);
                startActivity(i);
            }
        });


        btn_riwayat_kelembaban_udara = findViewById(R.id.btn_card_riwayat_kelembaban_udara);
        btn_riwayat_kelembaban_udara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuRiwayatActivity.this, RiwayatKelembabanUdaraActivity.class);
                startActivity(i);
            }
        });


        btn_riwayat_hujan = findViewById(R.id.btn_card_riwayat_hujan);
        btn_riwayat_hujan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuRiwayatActivity.this, RiwayatHujanActivity.class);
                startActivity(i);
            }
        });

        btn_riwayat_cahaya = findViewById(R.id.btn_card_riwayat_cahaya);
        btn_riwayat_cahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuRiwayatActivity.this, RiwayatCahayaActivity.class);
                startActivity(i);
            }
        });
    }
}