package com.example.user_login.ui.gallary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GallaryFragment extends Fragment {


    RecyclerView convoRecycle,otherRecycle;
    GalleryAdpater adpater;

    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_gallary, container, false);
        convoRecycle=view.findViewById(R.id.convoRecycler);
        otherRecycle=view.findViewById(R.id.otherRecycler);

        reference= FirebaseDatabase.getInstance().getReference().child("Gallery");

        getConvoImage();

        getOther();

        return view;
    }

    private void getOther() {
        reference.child("Other Event").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    String data=(String)snapshot.getValue();
                    imageList.add(data);

                }

                adpater=new GalleryAdpater(getContext(),imageList);

                otherRecycle.setLayoutManager(new GridLayoutManager(getContext(),3));
                otherRecycle.setHasFixedSize(true);
                otherRecycle.setAdapter(adpater);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getConvoImage() {
        reference.child("National event").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    String data=(String)snapshot.getValue();
                    imageList.add(data);

                }

                adpater=new GalleryAdpater(getContext(),imageList);

                convoRecycle.setLayoutManager(new GridLayoutManager(getContext(),3));
                convoRecycle.setHasFixedSize(true);
                convoRecycle.setAdapter(adpater);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}