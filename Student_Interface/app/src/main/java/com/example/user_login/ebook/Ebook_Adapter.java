package com.example.user_login.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_login.R;

import java.util.List;

public class Ebook_Adapter extends RecyclerView.Adapter<Ebook_Adapter.EbookViewHolder> {

    private Context context;
    private List<Ebook_data> list;

    public Ebook_Adapter(Context context, List<Ebook_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, final int position) {

        holder.ebookname.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,PdfViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfurl());
                context.startActivity(intent);
            }
        });


        holder.ebookdownlod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(holder.getAdapterPosition()).getPdfurl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {

        private TextView ebookname;
        private ImageView ebookdownlod;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            ebookdownlod=itemView.findViewById(R.id.ebookdownlod);
            ebookname=itemView.findViewById(R.id.ebookname);
        }
    }
}
