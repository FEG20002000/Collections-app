package com.iqcollections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class createCollection extends AppCompatActivity {
    private Button createCollection, imgSelection;
    private FirebaseAuth mAuth;
    private EditText name, description,goal;
    private ImageView imgDisplay;
    private FirebaseUser uid;
    private DatabaseReference collectionDbRef;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imgURI;
    String modelUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.edtName);
        description = findViewById(R.id.edtDesc);
        imgDisplay = findViewById(R.id.imgCollectionHold);
        imgSelection = findViewById(R.id.selectImg);
        createCollection = findViewById(R.id.btnCreateColl);
        goal = findViewById(R.id.edtGoal);

        collectionDbRef = FirebaseDatabase.getInstance().getReference().child("Collections");//setting the collection name
        uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user

        imgSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent ,2);//sending image intent
            }
        });

        createCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(imgURI);//uploads image then runs insert data with in it

                Context context = view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                Toast.makeText(createCollection.this, "Collection successfully created", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void insertCollectionData() {


        String colName = name.getText().toString();
        String colDescription = description.getText().toString();
        String colGoal = goal.getText().toString();

        Collections collections = new Collections(colName, colDescription,modelUri,colGoal);
        collectionDbRef.child(uid.getUid()).push().setValue(collections);
    }

    public  void uploadImage(Uri imgURI){//to get image to own storage for user
        if(imgURI != null){
            StorageReference fileRef = storageReference.child(uid.getUid()).child(System.currentTimeMillis()+"."+getFileExtension(imgURI));
            fileRef.putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            modelUri = uri.toString();
                            insertCollectionData();//has to run here or bugs out for what ever reason
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createCollection.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    private String getFileExtension(Uri imgURI) {//get image information
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return  mtm.getExtensionFromMimeType(cr.getType(imgURI));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 2 && resultCode == RESULT_OK&& data != null){
            imgURI = data.getData();
            imgDisplay.setImageURI(imgURI);//setting image data

        }


    }
}