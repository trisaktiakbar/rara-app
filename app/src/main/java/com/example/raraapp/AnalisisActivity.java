package com.example.raraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class AnalisisActivity extends AppCompatActivity {

    TextView value_T_avg_per_menit, value_T_avg_per_jam, value_T_avg_per_hari;
    TextView value_T_max_per_menit, value_T_max_per_jam, value_T_max_per_hari;
    TextView value_T_min_per_menit, value_T_min_per_jam, value_T_min_per_hari;

    TextView value_RH_avg_per_menit, value_RH_avg_per_jam, value_RH_avg_per_hari;
    TextView value_RH_max_per_menit, value_RH_max_per_jam, value_RH_max_per_hari;
    TextView value_RH_min_per_menit, value_RH_min_per_jam, value_RH_min_per_hari;

    TextView value_R_avg_per_menit, value_R_avg_per_jam, value_R_avg_per_hari;
    TextView value_R_max_per_menit, value_R_max_per_jam, value_R_max_per_hari;
    TextView value_R_min_per_menit, value_R_min_per_jam, value_R_min_per_hari;

    TextView value_ss_per_menit, value_ss_per_jam, value_ss_per_hari;

    TextView value_RR_hari_ini, value_RR_bulan_ini;

    double RR_hari_ini, RR_bulan_ini;

    int T_max_per_menit = 0;
    int T_min_per_menit = 100;
    double T_avg_per_menit;
    int T_max_per_jam = 0;
    int T_min_per_jam = 100;
    double T_avg_per_jam;
    int T_max_per_hari = 0;
    int T_min_per_hari = 100;
    double T_avg_per_hari;

    int RH_max_per_menit = 0;
    int RH_min_per_menit = 100;
    double RH_avg_per_menit;
    int RH_max_per_jam = 0;
    int RH_min_per_jam = 100;
    double RH_avg_per_jam;
    int RH_max_per_hari = 0;
    int RH_min_per_hari = 100;
    double RH_avg_per_hari;

    int R_max_per_menit = 0;
    int R_min_per_menit = 100;
    String R_avg_per_menit;
    int R_max_per_jam = 0;
    int R_min_per_jam = 100;
    String R_avg_per_jam;
    int R_max_per_hari = 0;
    int R_min_per_hari = 100;
    String R_avg_per_hari;

    int ss_per_menit = 0;
    int ss_per_jam = 0;
    int ss_per_hari = 0;

    Query db_ss, db_T, db_RH, db_R;

    Query ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis);

        value_T_avg_per_menit = findViewById(R.id.value_T_avg_per_menit);
        value_T_avg_per_jam = findViewById(R.id.value_T_avg_per_jam);
        value_T_avg_per_hari = findViewById(R.id.value_T_avg_per_hari);
        value_T_max_per_menit = findViewById(R.id.value_T_max_per_menit);
        value_T_max_per_jam = findViewById(R.id.value_T_max_per_jam);
        value_T_max_per_hari = findViewById(R.id.value_T_max_per_hari);
        value_T_min_per_menit = findViewById(R.id.value_T_min_per_menit);
        value_T_min_per_jam = findViewById(R.id.value_T_min_per_jam);
        value_T_min_per_hari = findViewById(R.id.value_T_min_per_hari);

        value_RH_avg_per_menit = findViewById(R.id.value_RH_avg_per_menit);
        value_RH_avg_per_jam = findViewById(R.id.value_RH_avg_per_jam);
        value_RH_avg_per_hari = findViewById(R.id.value_RH_avg_per_hari);
        value_RH_max_per_menit = findViewById(R.id.value_RH_max_per_menit);
        value_RH_max_per_jam = findViewById(R.id.value_RH_max_per_jam);
        value_RH_max_per_hari = findViewById(R.id.value_RH_max_per_hari);
        value_RH_min_per_menit = findViewById(R.id.value_RH_min_per_menit);
        value_RH_min_per_jam = findViewById(R.id.value_RH_min_per_jam);
        value_RH_min_per_hari = findViewById(R.id.value_RH_min_per_hari);

        value_R_avg_per_menit = findViewById(R.id.value_R_avg_per_menit);
        value_R_avg_per_jam = findViewById(R.id.value_R_avg_per_jam);
        value_R_avg_per_hari = findViewById(R.id.value_R_avg_per_hari);
        value_R_max_per_menit = findViewById(R.id.value_R_max_per_menit);
        value_R_max_per_jam = findViewById(R.id.value_R_max_per_jam);
        value_R_max_per_hari = findViewById(R.id.value_R_max_per_hari);
        value_R_min_per_menit = findViewById(R.id.value_R_min_per_menit);
        value_R_min_per_jam = findViewById(R.id.value_R_min_per_jam);
        value_R_min_per_hari = findViewById(R.id.value_R_min_per_hari);

        value_ss_per_menit = findViewById(R.id.value_ss_per_menit);
        value_ss_per_jam = findViewById(R.id.value_ss_per_jam);
        value_ss_per_hari = findViewById(R.id.value_ss_per_hari);

        db_ss = FirebaseDatabase.getInstance().getReference("Collection Data/Cahaya").orderByKey().limitToLast(60*24*30);
        db_ss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int counter = 0;

                int sum_per_menit = 0;
                double sum_per_jam = 0;
                double sum_per_hari = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (counter < 60){
                        sum_per_menit += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                        ss_per_menit += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    if(counter < (60*24)){
                        sum_per_jam += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                        ss_per_jam += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    if(counter < (60*24*30)){
                        sum_per_hari += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                        ss_per_hari += Integer.parseInt(dataSnapshot.child("Cahaya").getValue().toString());
                    }

                    counter++;
                }

                ss_per_menit = ss_per_menit / 60;
                ss_per_jam = ss_per_jam / 60;
                ss_per_hari = ss_per_hari / 60;

                DecimalFormat df = new DecimalFormat("#.##");

                double sum_per_jam_in_hours = sum_per_jam / 60;
                double sum_per_hari_in_hours = sum_per_hari / 60;

                value_ss_per_menit.setText(" : " + sum_per_menit + " menit");
                value_ss_per_jam.setText(" : " + df.format(sum_per_jam_in_hours) + " jam");
                value_ss_per_hari.setText(" : " + df.format(sum_per_hari_in_hours) + " jam");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        db_T = FirebaseDatabase.getInstance().getReference("Collection Data/Suhu").orderByKey().limitToLast(60*24*30);
        db_T.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int sum_per_menit = 0;
                double sum_per_jam = 0;
                double sum_per_hari = 0;
                int counter = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    int data = Integer.parseInt(dataSnapshot.child("Suhu").getValue().toString());
                    if (counter < 60){
                        sum_per_menit += data;

                        if(data > T_max_per_menit){
                            T_max_per_menit = data;
                        }

                        if(data < T_min_per_menit){
                            T_min_per_menit = data;
                        }
                    }

                    if(counter < (60*24)){
                        sum_per_jam += data;

                        if(data > T_max_per_jam){
                            T_max_per_jam = data;
                        }

                        if(data < T_min_per_jam){
                            T_min_per_jam = data;
                        }
                    }

                    if(counter < (60*24*30)){
                        sum_per_hari += data;

                        if(data > T_max_per_hari){
                            T_max_per_hari = data;
                        }

                        if(data < T_min_per_hari){
                            T_min_per_hari = data;
                        }
                    }

                    counter++;
                }

                DecimalFormat df = new DecimalFormat("#.##");

                double sum_per_menit_in_hours = sum_per_menit / 60;
                double sum_per_jam_in_hours = sum_per_jam / (60*24);
                double sum_per_hari_in_hours = sum_per_hari / (60*24*30);

                T_avg_per_menit = sum_per_menit_in_hours;
                T_avg_per_jam = sum_per_jam_in_hours;
                T_avg_per_hari = sum_per_hari_in_hours;

                value_T_avg_per_menit.setText(" : " + df.format(T_avg_per_menit) + " °C");
                value_T_avg_per_jam.setText(" : " + df.format(T_avg_per_jam) + " °C");
                value_T_avg_per_hari.setText(" : " + df.format(T_avg_per_hari) + " °C");

                value_T_max_per_menit.setText(" : " + T_max_per_menit + " °C");
                value_T_max_per_jam.setText(" : " + T_max_per_jam + " °C");
                value_T_max_per_hari.setText(" : " + T_max_per_hari + " °C");

                value_T_min_per_menit.setText(" : " + T_min_per_menit + " °C");
                value_T_min_per_jam.setText(" : " + T_min_per_jam + " °C");
                value_T_min_per_hari.setText(" : " + T_min_per_hari + " °C");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        db_RH = FirebaseDatabase.getInstance().getReference("Collection Data/Kelembaban Udara").orderByKey().limitToLast(60*24*30);
        db_RH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int sum_per_menit = 0;
                double sum_per_jam = 0;
                double sum_per_hari = 0;
                int counter = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    int data = Integer.parseInt(dataSnapshot.child("Kelembaban Udara").getValue().toString());
                    if (counter < 60){
                        sum_per_menit += data;

                        if(data > RH_max_per_menit){
                            RH_max_per_menit = data;
                        }

                        if(data < RH_min_per_menit){
                            RH_min_per_menit = data;
                        }
                    }

                    if(counter < (60*24)){
                        sum_per_jam += data;

                        if(data > RH_max_per_jam){
                            RH_max_per_jam = data;
                        }

                        if(data < RH_min_per_jam){
                            RH_min_per_jam = data;
                        }
                    }

                    if(counter < (60*24*30)){
                        sum_per_hari += data;

                        if(data > RH_max_per_hari){
                            RH_max_per_hari = data;
                        }

                        if(data < RH_min_per_hari){
                            RH_min_per_hari = data;
                        }
                    }

                    counter++;
                }

                DecimalFormat df = new DecimalFormat("#.##");

                double sum_per_menit_in_hours = sum_per_menit / 60;
                double sum_per_jam_in_hours = sum_per_jam / (60*24);
                double sum_per_hari_in_hours = sum_per_hari / (60*24*30);

                RH_avg_per_menit = sum_per_menit_in_hours;
                RH_avg_per_jam = sum_per_jam_in_hours;
                RH_avg_per_hari = sum_per_hari_in_hours;

                value_RH_avg_per_menit.setText(" : " + df.format(RH_avg_per_menit) + "%");
                value_RH_avg_per_jam.setText(" : " + df.format(RH_avg_per_jam) + "%");
                value_RH_avg_per_hari.setText(" : " + df.format(RH_avg_per_hari) + "%");

                value_RH_max_per_menit.setText(" : " + RH_max_per_menit + "%");
                value_RH_max_per_jam.setText(" : " + RH_max_per_jam + "%");
                value_RH_max_per_hari.setText(" : " + RH_max_per_hari + "%");

                value_RH_min_per_menit.setText(" : " + RH_min_per_menit + "%");
                value_RH_min_per_jam.setText(" : " + RH_min_per_jam + "%");
                value_RH_min_per_hari.setText(" : " + RH_min_per_hari + "%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        db_R = FirebaseDatabase.getInstance().getReference("Collection Data/Hujan").orderByKey().limitToLast(60*24*30);
        db_R.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int sum_per_menit = 0;
                double sum_per_jam = 0;
                double sum_per_hari = 0;
                int counter = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    int data = Integer.parseInt(dataSnapshot.child("Hujan").getValue().toString());
                    if (counter < 60){
                        sum_per_menit += data;

                        if(data > R_max_per_menit){
                            R_max_per_menit = data;
                        }

                        if(data < R_min_per_menit){
                            R_min_per_menit = data;
                        }
                    }

                    if(counter < (60*24)){
                        sum_per_jam += data;

                        if(data > R_max_per_jam){
                            R_max_per_jam = data;
                        }

                        if(data < R_min_per_jam){
                            R_min_per_jam = data;
                        }
                    }

                    if(counter < (60*24*30)){
                        sum_per_hari += data;

                        if(data > R_max_per_hari){
                            R_max_per_hari = data;
                        }

                        if(data < R_min_per_hari){
                            R_min_per_hari = data;
                        }
                    }

                    counter++;
                }

                DecimalFormat df = new DecimalFormat("#.##");

                double sum_per_menit_in_hours = sum_per_menit / 60;
                double sum_per_jam_in_hours = sum_per_jam / (60*24);
                double sum_per_hari_in_hours = sum_per_hari / (60*24*30);

                R_avg_per_menit = df.format(sum_per_menit_in_hours);
                R_avg_per_jam = df.format(sum_per_jam_in_hours);
                R_avg_per_hari = df.format(sum_per_hari_in_hours);

                value_R_avg_per_menit.setText(" : " + R_avg_per_menit + "%");
                value_R_avg_per_jam.setText(" : " + R_avg_per_jam + "%");
                value_R_avg_per_hari.setText(" : " + R_avg_per_hari + "%");

                value_R_max_per_menit.setText(" : " + R_max_per_menit + "%");
                value_R_max_per_jam.setText(" : " + R_max_per_jam + "%");
                value_R_max_per_hari.setText(" : " + R_max_per_hari + "%");

                value_R_min_per_menit.setText(" : " + R_min_per_menit + "%");
                value_R_min_per_jam.setText(" : " + R_min_per_jam + "%");
                value_R_min_per_hari.setText(" : " + R_min_per_hari + "%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        value_RR_hari_ini = findViewById(R.id.RR_hari_ini);
        value_RR_bulan_ini = findViewById(R.id.RR_bulan_ini);

        ref = FirebaseDatabase.getInstance().getReference("Collection Data");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RR_hari_ini = (T_avg_per_jam * (-1.366521107)) + (T_max_per_jam * 0.452117504) + (T_min_per_jam * 0.383445668) + (RH_avg_per_jam * 0.691153466) + (ss_per_jam * (-0.011276245)) + 0.214891672;
                RR_bulan_ini = (T_avg_per_hari * (-1.366521107)) + (T_max_per_hari  * 0.452117504) + (T_min_per_hari * 0.383445668) + (RH_avg_per_hari * 0.691153466) + (ss_per_hari * (-0.011276245)) + 0.214891672;

                DecimalFormat df = new DecimalFormat("#.##");
                value_RR_hari_ini.setText(df.format(RR_hari_ini) + " mm");
                value_RR_bulan_ini.setText(df.format(RR_bulan_ini) + " mm");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}