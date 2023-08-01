package com.example.user_login.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView deletenoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private  NoticaAdapter adapter;

    private DatabaseReference refernce;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=  inflater.inflate(R.layout.fragment_notice, container, false);


        deletenoticeRecycler =view.findViewById(R.id.deletenoticRecycler);
        progressBar=view.findViewById(R.id.progressbar);

        refernce= FirebaseDatabase.getInstance().getReference().child("Notice");


        deletenoticeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        deletenoticeRecycler.setHasFixedSize(true);

        getNotice();

        return view;
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
                adapter=new NoticaAdapter(getContext(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deletenoticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
