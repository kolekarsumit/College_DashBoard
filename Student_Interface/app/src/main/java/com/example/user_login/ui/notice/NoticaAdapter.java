package com.example.user_login.ui.notice;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.user_login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoticaAdapter extends RecyclerView.Adapter<NoticaAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;


    public NoticaAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfilditem_layout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        NoticeData currentItem = list.get(position);


        holder.DeletenoticeTital.setText(currentItem.getTitle());

        holder.date.setText(currentItem.getData());

        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null) {
                Glide.with(context).load(currentItem.getImage()).into(holder.deletenoticeImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView DeletenoticeTital, date, time;
        private ImageView deletenoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {

            super(itemView);

            DeletenoticeTital = itemView.findViewById(R.id.DeletenoticeTital);
            deletenoticeImage = itemView.findViewById(R.id.deletenoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }
    }
}
