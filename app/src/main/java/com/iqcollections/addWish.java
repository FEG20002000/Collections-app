package com.iqcollections;

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

public class addWish extends AppCompatActivity {

    private EditText etName;
    private EditText etDesc;
    private Uri wishImgUri;
    String wishUri;
    private FirebaseUser uid;
    private FirebaseAuth mAuth;
    private Button btnAdd, btnSelectWishImg;
    private ImageView imgWishDisp;
    private StorageReference wishReference = FirebaseStorage.getInstance().getReference();

    private DatabaseReference wishlistDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.wishName);
        etDesc = findViewById(R.id.wishDesc);
        imgWishDisp = findViewById(R.id.imgWish);
        btnSelectWishImg = findViewById(R.id.btnSelectWishImg);
        btnAdd = findViewById(R.id.btnAddWish);

        //Creating the wishlist table
        wishlistDbRef = FirebaseDatabase.getInstance().getReference().child("Wishlist");
        uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user

        btnSelectWishImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent ,2);//sending image intent
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(wishImgUri);//uploads image then runs insert data with in it

                Context context = view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                Toast.makeText(addWish.this, "Collection successfully created", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public  void uploadImage(Uri imgURI){//to get image to own storage for user
        if(wishImgUri != null){
            StorageReference fileRef = wishReference.child(uid.getUid()).child(System.currentTimeMillis()+"."+getFileExtension(wishImgUri));
            fileRef.putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            wishUri = uri.toString();
                            addWislistData();//has to run here or bugs out for what ever reason
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addWish.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
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

    private  void addWislistData(){
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Wish wish = new Wish(name, desc, wishUri);
        wishlistDbRef.child(uid.getUid()).push().setValue(wish);
    }

    private String getFileExtension(Uri wishImgUri) {//get image information
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return  mtm.getExtensionFromMimeType(cr.getType(wishImgUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 2 && resultCode == RESULT_OK&& data != null){
            wishImgUri = data.getData();
            imgWishDisp.setImageURI(wishImgUri);//setting image data

        }


    }
}