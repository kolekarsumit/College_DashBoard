package com.example.college_dashbord.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class AddTeachers extends AppCompatActivity {


    private ImageView addTeacherImage;
    private EditText addteachername,addteacheremail,addteachterpost;
    private Spinner addteachercategory;
    private Button addteacherbtn;
    private Bitmap bitmap=null;

    final private  int REQ=1;
    private  String category;
    private  String name,email,post,downlodurl="";
    private ProgressDialog pd;
    private  StorageReference storageReference;
    private DatabaseReference reference;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        addTeacherImage=findViewById(R.id.addTeacherImage);
        addteachername=findViewById(R.id.addTeachename);
        addteacheremail=findViewById(R.id.addTeacheEmail);
        addteachterpost=findViewById(R.id.addTeachepost);
        addteachercategory=findViewById(R.id.addTeacherCategory);
        addteacherbtn=findViewById(R.id.addTeachebtn);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference();

        pd=new ProgressDialog(this);




        String [] items=new String[] {"Select Category","Compute","IT","ENTC","Mechnical"};
        addteachercategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        addteachercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=addteachercategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherImage.setOnClickListener((view)->{openGallery();});

        addteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private  void checkValidation(){
        name=addteachername.getText().toString();
        email=addteacheremail.getText().toString();
        post=addteachterpost.getText().toString();

        if(name.isEmpty()){
            addteachername.setError("Empty");
            addteachername.requestFocus();
        }
        else if(email.isEmpty()){
            addteacheremail.setError("Empty");
            addteacheremail.requestFocus();
        }
        else if(post.isEmpty()){
            addteachterpost.setError("Empty");
            addteachterpost.requestFocus();
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
        }
        else if(bitmap==null){
            insertData();
        }
        else{
            uploadImage();
        }
    }

    private  void insertData(){

        dbRef=reference.child(category);
        final String uniqekey=dbRef.push().getKey();


        TeacherData teacherData=new TeacherData(name,email,post,downlodurl,uniqekey);

        dbRef.child(uniqekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddTeachers.this,"Faculty Added ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeachers.this,"Something wrong",Toast.LENGTH_SHORT).show();
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
        filePath=storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadtask=filePath.putBytes(finalimg);
        uploadtask.addOnCompleteListener(AddTeachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData();
                                }
                            });
                        }
                    });
                }
                else{
                    pd.dismiss();
                    Toast.makeText(AddTeachers.this,"Something Wrong",Toast.LENGTH_SHORT).show();

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
            addTeacherImage.setImageBitmap(bitmap);
        }
    }


}