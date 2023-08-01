package com.example.college_dashbord.notic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.college_dashbord.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeletenoticActivity extends AppCompatActivity {

   private RecyclerView  deletenoticeRecycler;
   private ProgressBar progressBar;
   private ArrayList<NoticeData> list;
   private  NoticaAdapter adapter;

   private DatabaseReference refernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletenotic);

        deletenoticeRecycler =findViewById(R.id.deletenoticRecycler);
        progressBar=findViewById(R.id.progressbar);

        refernce= FirebaseDatabase.getInstance().getReference().child("Notice");


        deletenoticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        deletenoticeRecycler.setHasFixedSize(true);
        
        getNotice();
    }

    private void getNotice() {
        refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    NoticeData data=snapshot.getValue(NoticeData.class);
                    list.add(data);
                }
                adapter=new NoticaAdapter(DeletenoticActivity.this,list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deletenoticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeletenoticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}