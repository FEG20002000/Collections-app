package com.iqcollections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createCollection extends AppCompatActivity {
    private Button createCollection, imgSelection;
    private FirebaseAuth mAuth;
    private EditText name, description;
    private ImageView imgDisplay;

    DatabaseReference collectionDbRef;

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

        collectionDbRef = FirebaseDatabase.getInstance().getReference().child("Collections");

        createCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCollectionData();
                Context context = view.getContext();
                Intent intent = new Intent(context, createCollection.class);
                startActivity(intent);
                Toast.makeText(createCollection.this, "Collection successfully created", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void insertCollectionData() {
        String colName = name.getText().toString();
        String colDescription = description.getText().toString();

        Collections collections = new Collections(colName, colDescription);
        collectionDbRef.push().setValue(collections);
    }
}