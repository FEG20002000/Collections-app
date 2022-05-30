package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addWish extends AppCompatActivity {

    private EditText etName;
    private EditText etDesc;
    private FirebaseUser uid;
    private FirebaseAuth mAuth;
    private Button btnAdd;
    private Button btnShowWish;

    private DatabaseReference wishlistDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);
        try {

            mAuth = FirebaseAuth.getInstance();
            etName = findViewById(R.id.wishName);
            etDesc = findViewById(R.id.wishDesc);
            btnAdd = findViewById(R.id.btnAddWish);
            btnShowWish = findViewById(R.id.btnShowWish);

            //Creating the wishlist table
            wishlistDbRef = FirebaseDatabase.getInstance().getReference().child("Wishlist");
            uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        addWislistData();
                }
            });

            btnShowWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(addWish.this, wishlist.class));
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "An error has occurred" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void addWislistData() {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();
        if (name.isEmpty() && desc.isEmpty()) {
            Toast.makeText(addWish.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {

            wishClass wishClass = new wishClass(name, desc);
            wishlistDbRef.child(uid.getUid()).push().setValue(wishClass);

            Toast.makeText(addWish.this, "Data inserted!", Toast.LENGTH_SHORT).show();
        }
    }


}