package com.example.raraapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RiwayatKelembabanUdaraActivity extends AppCompatActivity{

    private static final String TAG = "RiwayatKelembabanUdaraActivity";

    Query db_kelembaban_udara_per_menit, db_kelembaban_udara_per_jam, db_kelembaban_udara_per_hari;
    BarChart barChartPerMenit, barChartPerJam, barChartPerHari;
    ArrayList<BarEntry> data_per_menit, data_per_jam, data_per_hari;
    BarDataSet barDataSetPerMenit, barDataSetPerJam, barDataSetPerHari;
    BarData barDataPerMenit, barDataPerJam, barDataPerHari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_kelembaban_udara);


        barChartPerMenit = findViewById(R.id.chart_kelembaban_udara_per_menit);

        db_kelembaban_udara_per_menit = FirebaseDatabase.getInstance().getReference("Collection Data/Kelembaban Udara").orderByKey().limitToLast(60);
        db_kelembaban_udara_per_menit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_per_menit = new ArrayList<>();

                int entry_counter_per_menit = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(entry_counter_per_menit < 60) {
                        data_per_menit.add(new BarEntry(entry_counter_per_menit, Integer.parseInt(dataSnapshot.child("Kelembaban Udara").getValue().toString())));
                    }

                    entry_counter_per_menit++;
                }

                barDataSetPerMenit = new BarDataSet(data_per_menit, "Kelembaban Udara (% per menit)");
                barDataSetPerMenit.setColors(Color.rgb(0, 142, 207));

                barDataSetPerMenit.setValueTextColor(Color.rgb(0, 142, 207));

                barDataPerMenit = new BarData(barDataSetPerMenit);
                barChartPerMenit.setData(barDataPerMenit);
                barChartPerMenit.getDescription().setText(" ");
                barChartPerMenit.animateY(1);
                barChartPerMenit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        barChartPerJam = findViewById(R.id.chart_kelembaban_udara_per_jam);

        db_kelembaban_udara_per_jam = FirebaseDatabase.getInstance().getReference("Collection Data/Kelembaban Udara").orderByKey().limitToLast(60*24);
        db_kelembaban_udara_per_jam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_per_jam = new ArrayList<>();

                int entry_counter_per_jam = 0;
                int index_counter = 0;
                float sum = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    sum += Integer.parseInt(dataSnapshot.child("Kelembaban Udara").getValue().toString());

                    if((entry_counter_per_jam + 1) % 60 == 0) {
                        data_per_jam.add(new BarEntry(index_counter, sum/60));
                        index_counter++;
                        sum = 0;
                    }

                    entry_counter_per_jam++;
                }

                barDataSetPerJam = new BarDataSet(data_per_jam, "Kelembaban Udara (% per jam)");
                barDataSetPerJam.setColors(Color.rgb(0, 142, 207));

                barDataSetPerJam.setValueTextColor(Color.rgb(0, 142, 207));

                barDataPerJam = new BarData(barDataSetPerJam);
                barChartPerJam.setData(barDataPerJam);
                barChartPerJam.getDescription().setText(" ");
                barChartPerJam.animateY(1);
                barChartPerJam.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        barChartPerHari = findViewById(R.id.chart_kelembaban_udara_per_hari);

        db_kelembaban_udara_per_hari = FirebaseDatabase.getInstance().getReference("Collection Data/Kelembaban Udara").orderByKey().limitToLast(60*24*30);
        db_kelembaban_udara_per_hari.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_per_hari = new ArrayList<>();

                int entry_counter_per_hari = 0;
                int index_counter = 0;
                float sum = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    sum += Integer.parseInt(dataSnapshot.child("Kelembaban Udara").getValue().toString());

                    if((entry_counter_per_hari + 1) % (60*24) == 0) {
                        data_per_hari.add(new BarEntry(index_counter, sum/(60*24)));
                        index_counter++;
                        sum = 0;
                    }

                    entry_counter_per_hari++;
                }

                barDataSetPerHari = new BarDataSet(data_per_hari, "Kelembaban Udara (% per hari)");
                barDataSetPerHari.setColors(Color.rgb(0, 142, 207));

                barDataSetPerHari.setValueTextColor(Color.rgb(0, 142, 207));

                barDataPerHari = new BarData(barDataSetPerHari);
                barChartPerHari.setData(barDataPerHari);
                barChartPerHari.getDescription().setText(" ");
                barChartPerHari.animateY(1);
                barChartPerHari.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}