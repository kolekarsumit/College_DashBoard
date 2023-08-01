//package com.example.college_dashbord.Faculty;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.example.college_dashbord.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UpdateFaculty extends AppCompatActivity {
//
//
//    FloatingActionButton fab;
//    private RecyclerView cs,it,entc,mech;
//    private LinearLayout csno,itno,entcno,mechno;
//    private List<TeacherData> list1,list2,list3,list4;
//    private DatabaseReference reference,dbref;
//    private TeacherAdapter adapter;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_faculty);
//
//
//        mechno=findViewById(R.id.mechNodata);
//        entcno=findViewById(R.id.ENTCNodata);
//        itno=findViewById(R.id.itNodata);
//        csno=findViewById(R.id.csNodata);
//
//
//        cs=findViewById(R.id.csdepartment);
//        it=findViewById(R.id.itdepartment);
//        entc=findViewById(R.id.ENTCdepartment);
//        mech=findViewById(R.id.mechanicaldepartment);
//
//        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
//
//        csDepartment();
//        itDepartment();
//        entcDepartment();
//        mechDepartment();
//        fab=findViewById(R.id.fab);
//
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(UpdateFaculty.this,AddTeachers.class));
//
//
//            }
//        });
//    }
//
//    private void csDepartment() {
//        dbref=reference.child("Computer");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list1=new ArrayList<>();
//                if(!snapshot.exists()){
//                    csno.setVisibility(View.VISIBLE);
//                    cs.setVisibility(View.GONE);
//                }else{
//                    csno.setVisibility(View.GONE);
//                    cs.setVisibility(View.VISIBLE);
//                    for(DataSnapshot snapshot1: snapshot.getChildren()){
//                        TeacherData data=snapshot1.getValue(TeacherData.class);
//                        list1.add(data);
//                    }
//                    cs.setHasFixedSize(true);
//                    cs.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
//                    adapter=new TeacherAdapter(list1,UpdateFaculty.this);
//                    cs.setAdapter(adapter);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//}
//
//    private void itDepartment() {
//        dbref=reference.child("IT");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list2=new ArrayList<>();
//                if(!snapshot.exists()){
//                    itno.setVisibility(View.VISIBLE);
//                    it.setVisibility(View.GONE);
//
//                }
//                else{
//                    itno.setVisibility(View.GONE);
//                    it.setVisibility(View.VISIBLE);
//                    for(DataSnapshot snap: snapshot.getChildren()){
//                        TeacherData data=snap.getValue(TeacherData.class);
//                        list2.add(data);
//
//                    }
//                    it.setHasFixedSize(true);
//                    it.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
//                    adapter=new TeacherAdapter(list2,UpdateFaculty.this);
//                    it.setAdapter(adapter);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void entcDepartment() {
//        dbref=reference.child("ENTC");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list3=new ArrayList<>();
//                if(!snapshot.exists()){
//                    entcno.setVisibility(View.VISIBLE);
//                    entc.setVisibility(View.GONE);
//
//                }
//                else{
//                    entcno.setVisibility(View.GONE);
//                    entc.setVisibility(View.VISIBLE);
//                    for(DataSnapshot snap: snapshot.getChildren()){
//                        TeacherData data=snap.getValue(TeacherData.class);
//                        list3.add(data);
//
//                    }
//                    entc.setHasFixedSize(true);
//                    entc.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
//                    adapter=new TeacherAdapter(list3,UpdateFaculty.this);
//                    entc.setAdapter(adapter);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void mechDepartment() {
//        dbref=reference.child("Mechnical");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list4=new ArrayList<>();
//                if(!snapshot.exists()){
//                    mechno.setVisibility(View.VISIBLE);
//                    mech.setVisibility(View.GONE);
//
//                }
//                else{
//                    mechno.setVisibility(View.GONE);
//                    mech.setVisibility(View.VISIBLE);
//                    for(DataSnapshot snap: snapshot.getChildren()){
//                        TeacherData data=snap.getValue(TeacherData.class);
//                        list4.add(data);
//
//                    }
//                    mech.setHasFixedSize(true);
//                    mech.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
//                    adapter=new TeacherAdapter(list4,UpdateFaculty.this);
//                    mech.setAdapter(adapter);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//}

//package com.example.college_dashbord.Faculty;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import com.example.college_dashbord.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//public class UpdateFaculty extends AppCompatActivity {
//
//    FloatingActionButton fab;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_faculty);
//
//        fab=findViewById(R.id.fab);
//
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(UpdateFaculty.this, AddTeachers.class));
//
//
//            }
//        });
//    }
//}
package com.example.college_dashbord.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.college_dashbord.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

import java.util.ArrayList;
import java.util.List;
public class UpdateFaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csdepartment,itdepartment,entcdepartment,mechdepartment,civildepartment;
    private LinearLayout csNoData,itNoData,entcNoData,mechNoData,civilNoData;
    private List<TeacherData>list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;
    private DatabaseReference reference,dbRef;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        csNoData=findViewById(R.id.csNodata);
        csdepartment=findViewById(R.id.csdepartment);
        itNoData=findViewById(R.id.itNodata);
        itdepartment=findViewById(R.id.itdepartment);
        entcNoData=findViewById(R.id.ENTCNodata);
        entcdepartment=findViewById(R.id.ENTCdepartment);
        mechNoData=findViewById(R.id.mechNodata);
        mechdepartment=findViewById(R.id.mechanicaldepartment);
//        civildepartment=findViewById(R.id.civilDepartment);
//        civilNoData=findViewById(R.id.civilNoData);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        csdepartment();
        itdepartment();
        entcdepartment();
        mechdepartment();
//        civildepartment();

        fab=findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this,AddTeachers.class));

            }
        });
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
                    csdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list1,UpdateFaculty.this,"Compute");
                    csdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mechdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list4,UpdateFaculty.this,"IT");
                    mechdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    entcdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list3,UpdateFaculty.this,"ENTC");
                    entcdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    itdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list1,UpdateFaculty.this,"Mechnical");
                    itdepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void civildepartment() {
//        dbRef=reference.child("Civil");
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list5=new ArrayList<>();
//                if(!snapshot.exists()){
//                    civilNoData.setVisibility(View.VISIBLE);
//                    civildepartment.setVisibility(View.GONE);
//                }else{
//                    civilNoData.setVisibility(View.GONE);
//                    civildepartment.setVisibility(View.VISIBLE);
//                    for(DataSnapshot snapshot1: snapshot.getChildren()){
//                        TeacherData data=snapshot1.getValue(TeacherData.class);
//                        list5.add(data);
//                    }
//                    civildepartment.setHasFixedSize(true);
//                    civildepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
//                    adapter=new TeacherAdapter(list5,UpdateFaculty.this);
//                    civildepartment.setAdapter(adapter);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}