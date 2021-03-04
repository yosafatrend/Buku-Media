package com.adityaputrak.bukumedia.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaputrak.bukumedia.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Adapter.BukuAdapter;
import Api.ApiClient;
import Model.DataBuku;
import Model.ListBukuResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context;
    BukuAdapter adapter;
    private List<DataBuku> mItems = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        recyclerView = findViewById(R.id.rvBuku);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BukuAdapter(mItems, this);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "KLIKK", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DashboardAdminActivity.this, AddBukuActivity.class);
                startActivity(i);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        readData();
    }

    private void readData() {
        ApiClient.getServices().getListBuku().enqueue(new Callback<ListBukuResponse>() {
            @Override
            public void onResponse(Call<ListBukuResponse> call, Response<ListBukuResponse> response) {
                try {
                    mItems = response.body().getDataBuku();
                    recyclerView.setAdapter(new BukuAdapter(mItems, getApplicationContext()));
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Koneksi Error! " + e, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ListBukuResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}