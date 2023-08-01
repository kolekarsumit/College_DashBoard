package com.example.college_dashbord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImage extends AppCompatActivity {


    private Spinner Imagecategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;
    private  String category;
    private  final int REQ=1;
    private Bitmap bitmap;

    ProgressDialog pd;

    private StorageReference stroragereferance;
    private DatabaseReference reference;

    String downlodurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        selectImage=findViewById(R.id.addGalleryImage);
        Imagecategory=findViewById(R.id.image_category);
        uploadImage=findViewById(R.id.uploadImageBtn);
        galleryImageView=findViewById(R.id.galleryImageview);
        pd =new ProgressDialog(this);

        reference= FirebaseDatabase.getInstance().getReference().child("Gallery");
        stroragereferance= FirebaseStorage.getInstance().getReference().child("Gallery");


        String [] items=new String[] {"Select Category ","College days","National event ","Other Event "};
        Imagecategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        Imagecategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=Imagecategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap==null){
                    Toast.makeText(UploadImage.this, "please Upload Image", Toast.LENGTH_SHORT).show();
                }
                else if(category.equals("Select Category")){
                    Toast.makeText(UploadImage.this, "Please  select Image cateogry", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd.setMessage("Uploading ");
                    pd.show();
                    uploadImage();
                }
            }
        });



    }

    private void uploadImage(){

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filePath;
        filePath=stroragereferance.child(finalimg+"jpg");
        final UploadTask uploadtask=filePath.putBytes(finalimg);
        uploadtask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploaddata();
                                }
                            });
                        }
                    });
                }
                else{
                    pd.dismiss();
                    Toast.makeText(UploadImage.this,"Something Wrong",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private  void uploaddata(){
        reference=reference.child(category);
        final String uniqkey=reference.push().getKey();

        reference.child(uniqkey).setValue(downlodurl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Image Uloaded Sucessfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

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
            galleryImageView.setImageBitmap(bitmap);
        }
    }


}