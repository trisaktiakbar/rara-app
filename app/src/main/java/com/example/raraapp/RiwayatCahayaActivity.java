package com.example.raraapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RiwayatCahayaActivity extends AppCompatActivity{

    private static final String TAG = "RiwayatCahayaActivity";

    Query db_cahaya;

    TextView cahaya_per_menit, cahaya_per_jam, cahaya_per_hari;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_cahaya);

        cahaya_per_menit = findViewById(R.id.cahaya_per_menit);
        cahaya_per_jam = findViewById(R.id.cahaya_per_jam);
        cahaya_per_hari = findViewById(R.id.cahaya_per_hari);

        ll = findViewById(R.id.ll);


        db_cahaya = FirebaseDatabase.getInstance().getReference("Collection Data/Cahaya").orderByKey().limitToLast(60*24*30);
        db_cahaya.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int counter = 0;

                int sum_per_menit = 0;
                double sum_per_jam = 0;
                double sum_per_hari = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (counter < 60){
                        sum_per_menit += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    if(counter < (60*24)){
                        sum_per_jam += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    if(counter < (60*24*30)){
                        sum_per_hari += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    counter++;
                }

                DecimalFormat df = new DecimalFormat("#.##");

                double sum_per_jam_in_hours = sum_per_jam / 60;
                double sum_per_hari_in_hours = sum_per_hari / 60;

                cahaya_per_menit.setText(" : " + sum_per_menit + " menit");
                cahaya_per_jam.setText(" : " + df.format(sum_per_jam_in_hours) + " jam");
                cahaya_per_hari.setText(" : " + df.format(sum_per_hari_in_hours) + " jam");

                ll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}