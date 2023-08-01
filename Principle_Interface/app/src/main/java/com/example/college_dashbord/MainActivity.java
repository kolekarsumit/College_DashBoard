package com.example.college_dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.college_dashbord.Faculty.UpdateFaculty;
import com.example.college_dashbord.notic.DeletenoticActivity;
import com.example.college_dashbord.notic.UplodNotice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    CardView uplodNotice,addGalleryImage,addEbook,faculty,deletenotic;

    Button btn;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        btn=findViewById(R.id.logout);


        if(user==null){
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();

        }
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               Intent intent=new Intent(getApplicationContext(),Login.class);
               startActivity(intent);
               finish();

           }
       });









        uplodNotice=findViewById(R.id.addNotice);
        uplodNotice.setOnClickListener(this);

        addGalleryImage=findViewById(R.id.addGalleryImage);
        addGalleryImage.setOnClickListener(this);

        addEbook=findViewById(R.id.addEbook);
        addEbook.setOnClickListener(this);

        faculty=findViewById(R.id.faculty);
        faculty.setOnClickListener(this);

        deletenotic=findViewById(R.id.deletenotice);
        deletenotic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){


            case R.id.addNotice:
                 intent=new Intent(MainActivity.this, UplodNotice.class);
                startActivity(intent);
                break;

            case R.id.addGalleryImage:
                 intent=new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                break;

            case R.id.addEbook:
                intent=new Intent(MainActivity.this,UploadPDFActivity.class);
                startActivity(intent);
                break;

            case R.id.faculty:
                intent=new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent);
                break;

            case R.id.deletenotice:
                intent=new Intent(MainActivity.this, DeletenoticActivity.class);
                startActivity(intent);
                break;






        }



    }
}