package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class itemDetail extends AppCompatActivity {

    private TextView name, description, date;
    private ImageView imgItem;
    private String selectedItem;
    private FirebaseUser uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        //setting the objects
        name = findViewById(R.id.txtitemname);
        description = findViewById(R.id.txtitemDescrip);
        date = findViewById(R.id.txtitemDate);
        imgItem = findViewById(R.id.imgitemView);

        uid = FirebaseAuth.getInstance().getCurrentUser();
        try {
            Intent itemIntent = getIntent();
            selectedItem = itemIntent.getStringExtra("itemName");
            String itemDescription = itemIntent.getStringExtra("itemDescription");
            String itemDate = itemIntent.getStringExtra("itemDate");
            String itemImg = itemIntent.getStringExtra("itemIMG");

            name.setText("Name: " + selectedItem);
            description.setText("Description: " + itemDescription);
            date.setText("Date of Creation: " + itemDate);
            Picasso.get().load(itemImg).into(imgItem);

            ;

        } catch (Exception e) {
            Toast.makeText(this, "An error has occurred" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}