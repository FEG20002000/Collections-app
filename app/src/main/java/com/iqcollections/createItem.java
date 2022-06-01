package com.iqcollections;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class createItem extends AppCompatActivity {

    private EditText edtItemName, edtItemDescription;
    private DatePicker dtitemDate;
    private Button btnImageSelect, btnCreate;
    private ImageView imgItem;
    private FirebaseAuth mAuth;
    private FirebaseUser uid;
    private DatabaseReference itemDbReference;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imgURI;
    String modelUri;
    Boolean imgSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);


        edtItemDescription = findViewById(R.id.edtitemDesc);
        edtItemName = findViewById(R.id.edtItemname);
        dtitemDate = findViewById(R.id.dtpItemDate);
        btnImageSelect = findViewById(R.id.selectitemimg);
        btnCreate = findViewById(R.id.btnCreateitem);
        imgItem = findViewById(R.id.imgItemhold);

        itemDbReference = FirebaseDatabase.getInstance().getReference().child("Items");//setting the collection name
        uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user


        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSelected = true;
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);//sending image intent
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemName = edtItemName.getText().toString();
                String itemDescription = edtItemDescription.getText().toString();
                String itemDate = dtitemDate.getDayOfMonth() + " " + dtitemDate.getMonth() + " " + dtitemDate.getYear();

                if(itemName.isEmpty() || itemDescription.isEmpty() || itemDate.isEmpty() || imgSelected == false){
                    Toast.makeText(createItem.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage(imgURI);//uploads image then runs insert data with in it

                    Context context = view.getContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(createItem.this, "Item successfully created, please reselect collection", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void insertItemData() {


        String itemName = edtItemName.getText().toString();
        String itemDescription = edtItemDescription.getText().toString();
        String itemDate = dtitemDate.getDayOfMonth() + " " + dtitemDate.getMonth() + " " + dtitemDate.getYear();
        MainActivity mainActivity = new MainActivity();
        Intent intent = getIntent();
        String itemCollection = intent.getStringExtra("colName");//getting collection name

        Items items = new Items(itemName, itemDescription, modelUri, itemCollection, itemDate);
        itemDbReference.child(uid.getUid()).push().setValue(items);//setting the items values
    }

    public void uploadImage(Uri imgURI) {//to get image to own storage for user
        if (imgURI != null) {
            StorageReference fileRef = storageReference.child(uid.getUid()).child(System.currentTimeMillis() + "." + getFileExtension(imgURI));
            fileRef.putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            modelUri = uri.toString();
                            insertItemData();//has to run here or bugs out for what ever reason
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createItem.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
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
        return mtm.getExtensionFromMimeType(cr.getType(imgURI));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imgURI = data.getData();
            imgItem.setImageURI(imgURI);//setting image data

        }


    }

}