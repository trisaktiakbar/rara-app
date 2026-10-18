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

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    String username, password;
    EditText ed_username, ed_password;
    TextInputLayout username_text_input_layout, password_text_input_layout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username_text_input_layout = (TextInputLayout) findViewById(R.id.username_text_input_layout);
                password_text_input_layout = (TextInputLayout) findViewById(R.id.password_text_input_layout);

                username = ed_username.getText().toString();
                password = ed_password.getText().toString();

                if (username.length() == 0) {
                    //jika form username belum di isi / masih kosong
                    username_text_input_layout.setError("Username tidak boleh kosong!");
                    username_text_input_layout.setErrorEnabled(true);

                }
                else{
                    username_text_input_layout.setErrorEnabled(false);
                }
                if (password.length() == 0){
                    //jika form password belum di isi / masih kosong
                    password_text_input_layout.setError("Password tidak boleh kosong!");
                }
                else{
                    password_text_input_layout.setErrorEnabled(false);
                }

                if(username.length() != 0 && password.length() != 0) {
                    cekLogin();
                }
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    public void startRegister(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void cekLogin() {
        username = ed_username.getText().toString();
        password = ed_password.getText().toString();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            i.putExtra("Username", username);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}