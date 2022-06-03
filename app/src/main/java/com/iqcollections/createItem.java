package com.iqcollections;
/*
    Code Attribution :
    Source: YouTube
    URL link: https://www.youtube.com/watch?v=3YDOVD7n21E
    Title Page/Video: How to save user data into Firebase Realtime database using android studio.
    Author name/tag/channel: Be Codey
    Author channel/profile url link: https://www.youtube.com/c/BeCodey
 */
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class createItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    DrawerLayout dl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

//setting objects
        edtItemDescription = findViewById(R.id.edtitemDesc);
        edtItemName = findViewById(R.id.edtItemname);
        dtitemDate = findViewById(R.id.dtpItemDate);
        btnImageSelect = findViewById(R.id.selectitemimg);
        btnCreate = findViewById(R.id.btnCreateitem);
        imgItem = findViewById(R.id.imgItemhold);

        dl = findViewById(R.id.createItemLayout);
        nv = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, R.string.navi_open, R.string.navi_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        nv.bringToFront();
        nv.setNavigationItemSelectedListener(this);//setting navigation items

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
                String itemDate = dtitemDate.getDayOfMonth() + " " + dtitemDate.getMonth() + " " + dtitemDate.getYear();//storing the item information

                if(itemName.isEmpty() || itemDescription.isEmpty() || itemDate.isEmpty() || imgSelected == false){
                    Toast.makeText(createItem.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage(imgURI);//uploads image then runs insert data with in it

                    Context context = view.getContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();//sending to next screem
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
                            insertItemData();//running method
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;
            case R.id.nav_wish:
                intent = new Intent(this, wishlist.class);
                startActivity(intent);

                break;
            case R.id.nav_member:
                intent = new Intent(this, groupMembers.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(this, aboutDisplay.class);
                startActivity(intent);
                break;

        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}