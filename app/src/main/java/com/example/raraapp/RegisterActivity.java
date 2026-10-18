package com.example.raraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText ed_namadepan, ed_namabelakang, ed_email, ed_password, ed_password2;
    Button btn_daftar;
    String namadepan, namabelakang, email, password, password2;
    TextInputLayout namadepan_text_input_layout, namabelakang_text_input_layout, email_text_input_layout,
            password_text_input_layout, password2_text_input_layout;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        ed_namadepan = (EditText)findViewById(R.id.ed_namadepan);
        ed_namabelakang = (EditText)findViewById(R.id.ed_namabelakang);
        ed_email = (EditText)findViewById(R.id.ed_email);
        ed_password = (EditText)findViewById(R.id.ed_password);
        ed_password2 = (EditText)findViewById(R.id.ed_password2);

        namadepan_text_input_layout = (TextInputLayout)findViewById(R.id.namadepan_text_input_layout);
        namabelakang_text_input_layout = (TextInputLayout)findViewById(R.id.namabelakang_text_input_layout);
        email_text_input_layout = (TextInputLayout)findViewById(R.id.email_text_input_layout);
        password_text_input_layout = (TextInputLayout)findViewById(R.id.password_text_input_layout);
        password2_text_input_layout = (TextInputLayout)findViewById(R.id.password2_text_input_layout);

        btn_daftar = (Button)findViewById(R.id.btn_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namadepan = ed_namadepan.getText().toString();
                namabelakang = ed_namabelakang.getText().toString();
                email = ed_email.getText().toString();
                password = ed_password.getText().toString();
                password2 = ed_password2.getText().toString();

                if (namadepan.length() == 0) {
                    //jika form username belum di isi / masih kosong
                    namadepan_text_input_layout.setError("Nama Depan tidak boleh kosong!");
                    namadepan_text_input_layout.setErrorEnabled(true);

                }
                else{
                    namadepan_text_input_layout.setErrorEnabled(false);
                }

                if (email.length() == 0) {
                    //jika form username belum di isi / masih kosong
                    email_text_input_layout.setError("Email tidak boleh kosong!");
                    email_text_input_layout.setErrorEnabled(true);

                }
                else{
                    email_text_input_layout.setErrorEnabled(false);
                }

                if (password.length() == 0) {
                    //jika form username belum di isi / masih kosong
                    password_text_input_layout.setError("Password tidak boleh kosong!");
                    password_text_input_layout.setErrorEnabled(true);

                }
                else{
                    password_text_input_layout.setErrorEnabled(false);
                }

                if (!password.equals(password2)) {
                    //jika form username belum di isi / masih kosong
                    password2_text_input_layout.setError("Konfirmasi password salah!");
                    password2_text_input_layout.setErrorEnabled(true);
                }
                else{
                    password2_text_input_layout.setErrorEnabled(false);
                }

                if(namadepan.length() != 0 && namabelakang.length() != 0 && email.length() != 0 &&
                        password.length() != 0 && password2.length() != 0 && password.equals(password2)){

                    registrasi();
                }

                if (password2.length() == 0) {
                    //jika form username belum di isi / masih kosong
                    password2_text_input_layout.setError("Konfirmasi password tidak boleh kosong!");
                    password2_text_input_layout.setErrorEnabled(true);
                }
                else{
                    password2_text_input_layout.setErrorEnabled(false);
                }

            }
        });

    }

    private void registrasi() {
        namadepan = ed_namadepan.getText().toString();
        namabelakang = ed_namabelakang.getText().toString();
        email = ed_email.getText().toString();
        password = ed_password.getText().toString();
        password2 = ed_password2.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Intent v = new Intent(getApplicationContext(),MainActivity.class);

                            databaseReference = FirebaseDatabase.getInstance().getReference("User/" + mAuth.getUid());
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Nama Depan",namadepan);
                            hashMap.put("Nama Belakang",namabelakang);
                            hashMap.put("Email", email);
                            hashMap.put("Hak Akses", "User");

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                    }
                                    else{
                                    }
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_LONG).show();
                            startActivity(v);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}