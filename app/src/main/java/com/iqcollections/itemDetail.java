package com.iqcollections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
            checkitem.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String desc = snapshot.child(selectedItem).child("itemDescription").getValue(String.class);
                        String itemDate = snapshot.child(selectedItem).child("itemDate").getValue(String.class);
                        String itemName = snapshot.child(selectedItem).child("itemName").getValue(String.class);
                        String itemImage = snapshot.child(selectedItem).child("itemImage").getValue(String.class);

                        name.setText(itemName);
                        description.setText(desc);
                        date.setText("Date: "+itemDate);
                        imgItem.setImageURI(Uri.parse(itemImage));
                     }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }catch (Exception e){

        }




    }
}