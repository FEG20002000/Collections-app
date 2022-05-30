package com.iqcollections;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class itemDetail extends AppCompatActivity {

    private TextView name,description,date;
    private ImageView imgItem;
    private String selectedItem;
    private FirebaseUser uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        name = findViewById(R.id.txtitemname);
        description = findViewById(R.id.txtitemDescrip);
        date = findViewById(R.id.txtitemDate);
        imgItem = findViewById(R.id.imgitemView);
        listItem lstIt = new listItem();
        selectedItem = lstIt.getSelectedItem();
        uid = FirebaseAuth.getInstance().getCurrentUser();
        Query checkitem = FirebaseDatabase.getInstance().getReference("Items").child(uid.getUid()).orderByChild("itemName").equalTo(selectedItem);
        try{



        }catch (Exception e){

        }




    }
}