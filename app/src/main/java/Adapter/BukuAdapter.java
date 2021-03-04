package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaputrak.bukumedia.R;
import com.adityaputrak.bukumedia.admin.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import Model.DataBuku;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {

    private List<DataBuku> mdata;
    private Context context;

    public BukuAdapter(List<DataBuku> mdata, Context context) {
        this.mdata = mdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buku,parent, false);
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

        Glide.with(context)
                .load(BASEIMAGE + dataBuku.getCover()).into(holder.Cover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailActivity.class);
                i.putExtra("ID", mdata.get(position).getId());
                i.putExtra("JUDUL", mdata.get(position).getJudul());
                i.putExtra("DESKRIPSI", mdata.get(position).getDeskripsi());
                i.putExtra("HARGA", mdata.get(position).getHarga());
                i.putExtra("STOK", mdata.get(position).getStok());
                i.putExtra("COVER", mdata.get(position).getCover());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Judul, Deskripsi, Harga, Stok;
        ImageView Cover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Judul = itemView.findViewById(R.id.tvJudul);
            Deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Cover = itemView.findViewById(R.id.ivCover);
        }

        public void setJudul(String judul) { Judul.setText(judul);}
        public void setDeskripsi(String deskripsi) { Deskripsi.setText(deskripsi);}
        public void setHarga(String harga) { Harga.setText(harga);}
        public void setStok(String stok) { Stok.setText(stok);}
        public void setCover(ImageView cover) { this.Cover = cover;}
    }
}
