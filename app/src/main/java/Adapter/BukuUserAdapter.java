package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaputrak.bukumedia.R;
import com.adityaputrak.bukumedia.admin.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import Model.DataBuku;
import Model.TotalSeluruhnya;

public class BukuUserAdapter extends RecyclerView.Adapter<BukuUserAdapter.ViewHolder> {

    private List<DataBuku> mdata;
    private Context context;

    public BukuUserAdapter(List<DataBuku> mdata, Context context) {
        this.mdata = mdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data_buku,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataBuku dataBuku = mdata.get(position);

        String BASEIMAGE = "http://192.168.107.59:8000/storage/";

        holder.setJudul(dataBuku.getJudul());
        holder.setDeskripsi(dataBuku.getDeskripsi());
        holder.setHarga(String.valueOf(dataBuku.getHarga()));
        holder.setStok(String.valueOf(dataBuku.getStok()));
        holder.setTotalStok(dataBuku.getStok());
        holder.setHargaBuku(dataBuku.getHarga());

        Glide.with(context)
                .load(BASEIMAGE + dataBuku.getCover()).into(holder.Cover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Judul, Deskripsi, Harga, Stok, Jumlah, TotalQty;
        ImageView Cover;
        Button btKurang, btTambah;
        int dataJumlah, Total, HargaBuku, TotalStok;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Judul = itemView.findViewById(R.id.tvJudul);
            Deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Cover = itemView.findViewById(R.id.ivCover);
            Jumlah = itemView.findViewById(R.id.tvJumlah);
            TotalQty = itemView.findViewById(R.id.tvTotalQty);
            btKurang = itemView.findViewById(R.id.btKurangi);
            btTambah = itemView.findViewById(R.id.btTambah);
            dataJumlah = 0;
            Total = 0;

            btTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        dataJumlah = dataJumlah + 1;
                        Jumlah.setText(String.valueOf(dataJumlah));
                        Total = dataJumlah * HargaBuku;
                        TotalQty.setText(String.valueOf(Total));

                        TotalSeluruhnya.TOTALAKHIR = TotalSeluruhnya.TOTALAKHIR + HargaBuku;
                }
            });

            btKurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataJumlah == 0){

                    }else{
                        dataJumlah = dataJumlah - 1;
                        Jumlah.setText(String.valueOf(dataJumlah));
                        Total = dataJumlah * HargaBuku;
                        TotalQty.setText(String.valueOf(Total));

                        TotalSeluruhnya.TOTALAKHIR = TotalSeluruhnya.TOTALAKHIR - HargaBuku;
                    }
                }
            });

        }

        public void setJudul(String judul) { Judul.setText(judul);}
        public void setDeskripsi(String deskripsi) { Deskripsi.setText(deskripsi);}
        public void setHarga(String harga) { Harga.setText(harga);}
        public void setStok(String stok) { Stok.setText(stok);}
        public void setCover(ImageView cover) { this.Cover = cover;}
        public void setTotalQty (TextView totalQty) { TotalQty = totalQty;}
        public void setTotalStok (int totalStok) { this.TotalStok = totalStok;}
        public void setHargaBuku (int hargaBuku) {
            HargaBuku = hargaBuku;
        }
    }
}
