package com.example.college_dashbord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class UploadPDFActivity extends AppCompatActivity {


    private CardView addPdf;
    private final int REQ=1;
    private Uri pdfdata;
    private EditText pdfTital;
    private Button uplodpdfBtn;
    private DatabaseReference databaseReference;
    private StorageReference storagereferance;
    String downlodurl="";
    private ProgressDialog pd;
    private TextView pdftextview;
    private  String pdfname;
    private String title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdfactivity);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        storagereferance= FirebaseStorage.getInstance().getReference();

        pd=new ProgressDialog(this);

        addPdf=findViewById(R.id.addPdf);
        pdfTital=findViewById(R.id.pdftitle);
        uplodpdfBtn=findViewById(R.id.uploadPdfBtn);
        pdftextview=findViewById(R.id.pdftextview);

        addPdf.setOnClickListener((view)->{openGallery();});
        uplodpdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=pdfTital.getText().toString();
                if(title.isEmpty()){
                    pdfTital.setError("Empty");
                    pdfTital.requestFocus();
                }
                else if(pdfdata==null){
                    Toast.makeText(UploadPDFActivity.this, "Pleasa Upload pdf", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadpdf();
                }
            }
        });


    }
    private  void uploadpdf(){
        pd.setTitle("Please wait");
        pd.setMessage("Uploadin pdf");
        pd.show();
        StorageReference refernce=storagereferance.child("pdf/"+pdfname+"-"+System.currentTimeMillis()+".pdf");
        refernce.putFile(pdfdata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());

                Uri uri=uriTask.getResult();
                UplodData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDFActivity.this, "Some went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void UplodData(String downlodurl){

        String  uniqekey =databaseReference.child("pdf").push().getKey();

        HashMap data=new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downlodurl);

        databaseReference.child("pdf").child(uniqekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPDFActivity.this, "pdf uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                pdfTital.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDFActivity.this, "failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void openGallery(){
        Intent intent=new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF"),REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ&&resultCode==RESULT_OK){
           pdfdata=data.getData();
           if(pdfdata.toString().startsWith("content://")){

               Cursor cursor=null;

               try {
                   cursor=UploadPDFActivity.this.getContentResolver().query(pdfdata,null,null,null,null);
                   if(cursor!=null&&cursor.moveToFirst()){
                       pdfname=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                   }
               } catch (Exception e) {

                   throw new RuntimeException(e);
               }
           }
           else if(pdfdata.toString().startsWith("file://")){
               pdfname=new File(pdfdata.toString()).getName();

           }
           pdftextview.setText(pdfname);
        }
    }
}