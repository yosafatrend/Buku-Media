package com.adityaputrak.bukumedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adityaputrak.bukumedia.R;

import Api.ApiClient;
import Model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtNama, edtEmail, edtPassword;
    Button btnRegist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNama = findViewById(R.id.etNama);
        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        btnRegist = findViewById(R.id.btRegister);
        
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesRegistrasi();
            }
        });
    }

    private void prosesRegistrasi() {
        String nama, email, pass;
        nama = edtNama.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPassword.getText().toString();

        ApiClient.getServices().registerUser(nama, email, pass, "CUSTOMER")
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus() == 1){
                                Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                            }
                            else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error " + t, Toast.LENGTH_SHORT).show();                    }
                });
    }
}