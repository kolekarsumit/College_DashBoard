package com.example.college_dashbord.notic;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UplodNotice extends AppCompatActivity {

    private CardView addImage;
    private final int REQ=1;
    private Bitmap bitmap;
    private EditText noticeTital;
    private Button uplodnoticeBtn;
    private ImageView noticeImageview;
    private DatabaseReference reference,dbRef;
    private StorageReference stroragereferance;
    String downlodurl="";
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_uplod_notice);
        addImage=findViewById(R.id.addImage);
        noticeTital=findViewById(R.id.noticeTital);
        noticeImageview=findViewById(R.id.noticImageview);
        uplodnoticeBtn=findViewById(R.id.uploadNoticBtn);
        reference= FirebaseDatabase.getInstance().getReference();
        stroragereferance= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

      uplodnoticeBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                      if(noticeTital.getText().toString().isEmpty()){
                          noticeTital.setError("Empty");
                          noticeTital.requestFocus();
                      }else  if(bitmap==null){

                          uploaddata();
                      }
                      else{
                          uploadImage();
                      }
          }
      });
    }

    private void uploadImage(){

        pd.setMessage("Uploding");
        pd.show();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filePath;
        filePath=stroragereferance.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadtask=filePath.putBytes(finalimg);
        uploadtask.addOnCompleteListener(UplodNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(UplodNotice.this,"Something Wrong",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private  void uploaddata()
    {
        dbRef=reference.child("Notice");
        final String uniqekey=dbRef.push().getKey();

        String title=noticeTital.getText().toString();

        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("dd-MM-yy");
        String date=currentdate.format(calfordate.getTime());

        Calendar calfortime=Calendar.getInstance();
        SimpleDateFormat currtime=new SimpleDateFormat("hh:mm a");
        String time=currtime.format(calfortime.getTime());

        NoticeData noticedate=new NoticeData(title,downlodurl,date,time,uniqekey);

        dbRef.child(uniqekey).setValue(noticedate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UplodNotice.this,"Notice Uploded",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UplodNotice.this,"Something wrong",Toast.LENGTH_SHORT).show();
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
            noticeImageview.setImageBitmap(bitmap);
        }
    }
}