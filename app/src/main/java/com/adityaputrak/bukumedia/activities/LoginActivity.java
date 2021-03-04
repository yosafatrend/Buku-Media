package com.adityaputrak.bukumedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adityaputrak.bukumedia.R;
import com.adityaputrak.bukumedia.admin.DashboardAdminActivity;

public class LoginActivity extends AppCompatActivity {

    public static EditText edtUsername, edtPassword;
    public static ImageView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.etUsername);
        edtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginProcces();
                finish();
            }
        });
    }

    private void LoginProcces() {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        if (username.equals("admin") && password.equals("admin")){
            startActivity(new Intent(this, DashboardAdminActivity.class));
            Toast.makeText(this, "Selamat datang admin", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show();
        }
    }
}