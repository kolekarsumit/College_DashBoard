package com.example.user_login.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class facultyFragment extends Fragment {

    private RecyclerView csdepartment,itdepartment,entcdepartment,mechdepartment,civildepartment;
    private LinearLayout csNoData,itNoData,entcNoData,mechNoData,civilNoData;
    private List<TeacherData> list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;
    private DatabaseReference reference,dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_faculty, container, false);


        csNoData=view.findViewById(R.id.csNodata);
        csdepartment=view.findViewById(R.id.csdepartment);
        itNoData=view.findViewById(R.id.itNodata);
        itdepartment=view.findViewById(R.id.itdepartment);
        entcNoData=view.findViewById(R.id.ENTCNodata);
        entcdepartment=view.findViewById(R.id.ENTCdepartment);
        mechNoData=view.findViewById(R.id.mechNodata);
        mechdepartment=view.findViewById(R.id.mechanicaldepartment);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        csdepartment();
        itdepartment();
        entcdepartment();
        mechdepartment();

        return  view;


    }
    private void csdepartment() {
        dbRef=reference.child("Compute");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csdepartment.setVisibility(View.GONE);
                }else{
                    csNoData.setVisibility(View.GONE);
                    csdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csdepartment.setHasFixedSize(true);
                    csdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list1,getContext());
                    csdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mechdepartment() {
        dbRef=reference.child("IT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if(!snapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    mechdepartment.setVisibility(View.GONE);
                }else{
                    mechNoData.setVisibility(View.GONE);
                    mechdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    mechdepartment.setHasFixedSize(true);
                    mechdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list4,getContext());
                    mechdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void entcdepartment() {
        dbRef=reference.child("ENTC");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if(!snapshot.exists()){
                    entcNoData.setVisibility(View.VISIBLE);
                    entcdepartment.setVisibility(View.GONE);
                }else{
                    entcNoData.setVisibility(View.GONE);
                    entcdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    entcdepartment.setHasFixedSize(true);
                    entcdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list3,getContext());
                    entcdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void itdepartment() {
        dbRef=reference.child("Mechnical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if(!snapshot.exists()){
                    itNoData.setVisibility(View.VISIBLE);
                    itdepartment.setVisibility(View.GONE);
                }else{
                    itNoData.setVisibility(View.GONE);
                    itdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    itdepartment.setHasFixedSize(true);
                    itdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list2,getContext());
                    itdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}