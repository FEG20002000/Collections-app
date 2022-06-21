package com.iqcollections;
/*
    Code Attribution 1:
    Source: YouTube
    URL link: https://www.youtube.com/watch?v=3YDOVD7n21E
    Title Page/Video: How to save user data into Firebase Realtime database using android studio.
    Author name/tag/channel: Be Codey
    Author channel/profile url link: https://www.youtube.com/c/BeCodey
 */

/*
    Code Attribution 2:
    Source: YouTube
    URL link: https://youtu.be/9JdbgoYgCyA
    Title Page/Video: Store Firebase Realtime Database in Android Studio 2021 | Firebase Android CRUD Operation
    Author name/tag/channel: Cambo Tutorial
    Author channel/profile url link: https://m.youtube.com/c/CamboTutorial
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

public class createCollection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button createCollection, imgSelection;
    private FirebaseAuth mAuth;
    private EditText name, description, goal;
    private ImageView imgDisplay;
    private FirebaseUser uid;
    private DatabaseReference collectionDbRef;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imgURI;
    String modelUri;
    Boolean imgSelected = false;
    DrawerLayout dl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);
        try {
            mAuth = FirebaseAuth.getInstance();
            name = findViewById(R.id.edtName);
            description = findViewById(R.id.edtDesc);
            imgDisplay = findViewById(R.id.imgCollectionHold);
            imgSelection = findViewById(R.id.selectImg);
            createCollection = findViewById(R.id.btnCreateColl);
            goal = findViewById(R.id.edtGoal);

            dl = findViewById(R.id.createColLayout);
            nv = findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, R.string.navi_open, R.string.navi_close);
            dl.addDrawerListener(toggle);
            toggle.syncState();
            nv.bringToFront();
            nv.setNavigationItemSelectedListener(this);

            collectionDbRef = FirebaseDatabase.getInstance().getReference().child("Collections");//setting the collection name
            uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user

            imgSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgSelected = true;
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, 2);//sending image intent
                }
            });

            createCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String colName = name.getText().toString();
                    String colDescription = description.getText().toString();
                    String colGoal = goal.getText().toString();


                    if (colDescription.isEmpty() || colName.isEmpty() || colGoal.isEmpty() || imgSelected == false) {
                        Toast.makeText(createCollection.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadImage(imgURI);//uploads image then runs insert data with in it

                        Context context = view.getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(createCollection.this, "Collection successfully created", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "An error has occurred" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void insertCollectionData() {

        String colName = name.getText().toString();
        String colDescription = description.getText().toString();
        String colGoal = goal.getText().toString();

        Collections collections = new Collections(colName, colDescription, modelUri, colGoal);
        collectionDbRef.child(uid.getUid()).push().setValue(collections);
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

                                insertCollectionData();//has to run here or bugs out for what ever reason
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createCollection.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(createCollection.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
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
            if (imgURI == null) {
                Toast.makeText(createCollection.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            } else {
                imgDisplay.setImageURI(imgURI);//setting image data
            }
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