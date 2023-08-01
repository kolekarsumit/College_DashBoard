package com.example.college_dashbord.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.college_dashbord.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UdateTeacherActivity extends AppCompatActivity {

    private ImageView updateteacherimage;
    private EditText updateteachername,updateteacherpost,updateteacheremail;
    private Button updateteacherbtn,deleteteacherbtn;
    private String name, email,image,post;
    private final int REQ=1;

    private Bitmap bitmap=null;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private  String downlodurl;
    private String uniqekey,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        image = getIntent().getStringExtra("image");
        uniqekey=getIntent().getStringExtra("key");
        category=getIntent().getStringExtra("category");


        updateteachername = findViewById(R.id.updateteachername);
        updateteacherimage = findViewById(R.id.updateteacherImage);
        updateteacherpost = findViewById(R.id.updateteacherpost);
        updateteacheremail = findViewById(R.id.updateteacheremail);
        updateteacherbtn = findViewById(R.id.UpdateTeacherbtn);
        deleteteacherbtn = findViewById(R.id.DeleteTeacherbtn);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference();


        try {
            Picasso.get().load(image).into(updateteacherimage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateteacheremail.setText(email);
        updateteachername.setText(name);
        updateteacherpost.setText(post);

        updateteacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        updateteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=updateteachername.getText().toString();
                email=updateteacheremail.getText().toString();
                post=updateteacherpost.getText().toString();

                checkValidation();
            }
        });

        deleteteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private  void deleteData(){
        reference.child(category).child(uniqekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UdateTeacherActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UdateTeacherActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void checkValidation(){

        if(name.isEmpty()){
            updateteachername.setError("Empty");
            updateteachername.requestFocus();

        }
        else if(post.isEmpty()){
            updateteacherpost.setError("Empty");
            updateteacherpost.requestFocus();
        }
        else if(email.isEmpty()){
            updateteacheremail.setError("Empty");
            updateteacheremail.requestFocus();

        }
        else if(bitmap==null){
            updateData(image);
        }
        else{
            uploadimage();
        }

    }
    private void updateData(String s){
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        hp.put("image",s);


        reference.child(category).child(uniqekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(UdateTeacherActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UdateTeacherActivity.this, "Someting goan a wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadimage(){

//        pd.setMessage("Uploding");
//        pd.show();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadtask=filePath.putBytes(finalimg);
        uploadtask.addOnCompleteListener(UdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    downlodurl=String.valueOf(uri);
                                    updateData(downlodurl);
                                }
                            });
                        }
                    });
                }
                else{
//                    pd.dismiss();
                    Toast.makeText(UdateTeacherActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private  void openGallery(){
            Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickImage,REQ);
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==REQ&&resultCode==RESULT_OK){
                Uri uri=data.getData();
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                updateteacherimage.setImageBitmap(bitmap);
            }
        }





}