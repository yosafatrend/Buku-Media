package com.adityaputrak.bukumedia.admin;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adityaputrak.bukumedia.R;

import java.io.File;

import Api.ApiClient;
import Model.DetailBukuResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class AddBukuActivity extends AppCompatActivity {
    EditText edtJudul, edtDeskripsi, edtHarga, edtStok;
    ImageView ivCover;
    Button btnGambar, btnAdd;
    TextView tvCover;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);

        edtJudul = findViewById(R.id.etJudul);
        edtDeskripsi = findViewById(R.id.etDeskripsi);
        edtHarga = findViewById(R.id.etHarga);
        edtStok = findViewById(R.id.etStok);
        ivCover = findViewById(R.id.ivCover);
        tvCover = findViewById(R.id.tvGambar);
        btnGambar = findViewById(R.id.btGambar);
        btnAdd = findViewById(R.id.btTambah);

        String[] galleryPermissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {

        } else {
            EasyPermissions.requestPermissions(this, "Access for Storage", 101, galleryPermissions);
        }

        btnGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahGambar();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimData();
            }
        });
    }

    private void tambahGambar() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        galleryIntent = Intent.createChooser(galleryIntent, "Choose a file");
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                tvCover.setText("lokasi file" + mediaPath);
                ivCover.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
            }
            else{
                Toast.makeText(this, "File belum ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Throwable e){
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void kirimData() {
        final String judul = edtJudul.getText().toString();
        final String deskripsi = edtDeskripsi.getText().toString();
        final String harga = edtHarga.getText().toString();
        final String stok = edtStok.getText().toString();

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("*/*"));
        MultipartBody.Part fileToUpload = MultipartBody.Part
                .createFormData("gambar", file.getName(), requestBody);
        RequestBody dataJudul = RequestBody.create(judul, MediaType.parse("multipart/form-data"));
        RequestBody dataDeskripsi = RequestBody.create(deskripsi, MediaType.parse("multipart/form-data"));
        RequestBody dataHarga = RequestBody.create(harga, MediaType.parse("multipart/form-data"));
        RequestBody dataStok = RequestBody.create(stok, MediaType.parse("multipart/form-data"));

        ApiClient.getServices().createBuku(dataJudul, dataDeskripsi, fileToUpload, dataHarga, dataStok)
                .enqueue(new Callback<DetailBukuResponse>() {
                    @Override
                    public void onResponse(Call<DetailBukuResponse> call, Response<DetailBukuResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus() == 1){
                                Toast.makeText(AddBukuActivity.this,
                                        "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddBukuActivity.this, DashboardAdminActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(AddBukuActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailBukuResponse> call, Throwable t) {

                    }
                });
    }
}