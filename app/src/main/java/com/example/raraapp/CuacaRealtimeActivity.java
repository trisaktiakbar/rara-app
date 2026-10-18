package com.example.raraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CuacaRealtimeActivity extends AppCompatActivity {

    DatabaseReference myRef;

    String value_cahaya, value_hujan, value_kelembaban_udara, value_suhu;
    TextView text_persentase_cahaya, text_persentase_hujan, text_persentase_kelembaban_udara, text_persentase_suhu;
    TextView text_status_hujan, text_status_kelembaban_udara, text_status_suhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca_realtime);

        text_persentase_cahaya = findViewById(R.id.text_persentase_cahaya);
        text_persentase_hujan = findViewById(R.id.text_persentase_hujan);
        text_persentase_kelembaban_udara = findViewById(R.id.text_persentase_kelembaban_udara);
        text_persentase_suhu = findViewById(R.id.text_persentase_suhu);

        text_status_hujan = findViewById(R.id.text_status_hujan);
        text_status_kelembaban_udara = findViewById(R.id.text_status_kelembaban_udara);
        text_status_suhu = findViewById(R.id.text_status_suhu);

        myRef = FirebaseDatabase.getInstance().getReference("Current Data");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value_cahaya = snapshot.child("Cahaya").getValue().toString();
                value_kelembaban_udara = snapshot.child("Kelembaban Udara").getValue().toString();
                value_suhu = snapshot.child("Suhu").getValue().toString();
                value_hujan = snapshot.child("Hujan").getValue().toString();

                if(Integer.parseInt(value_cahaya) == 0){
                    text_persentase_cahaya.setText("Gelap");
                }
                else{
                    text_persentase_cahaya.setText("Cerah");
                }


                text_persentase_hujan.setText(value_hujan + "%");
                if(Integer.parseInt(value_hujan) > 20){
                    text_status_hujan.setText("Hujan Ringan");
                }
                if(Integer.parseInt(value_hujan) > 50){
                    text_persentase_hujan.setText("Hujan Deras");
                }


                text_persentase_kelembaban_udara.setText(value_kelembaban_udara + "%");
                if(Integer.parseInt(value_kelembaban_udara) < 78 ){
                    text_status_kelembaban_udara.setText("Kering");
                }
                else if(Integer.parseInt(value_kelembaban_udara) > 90 ){
                    text_status_kelembaban_udara.setText("Lembab");
                }
                else{
                    text_status_kelembaban_udara.setText("Normal");
                }

                text_persentase_suhu.setText(value_suhu + "°C");
                if(Integer.parseInt(value_suhu) < 26){
                    text_status_suhu.setText("Dingin");
                }
                else if(Integer.parseInt(value_suhu) > 37){
                    text_status_suhu.setText("Panas");
                }
                else{
                    text_status_suhu.setText("Normal");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}