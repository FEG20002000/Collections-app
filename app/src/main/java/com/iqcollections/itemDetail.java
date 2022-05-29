package com.iqcollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class itemDetail extends AppCompatActivity {

    private TextView name,description,date;
    private ImageView imgItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        name = findViewById(R.id.txtitemname);
        description = findViewById(R.id.txtitemDescrip);
        date = findViewById(R.id.txtitemDate);
        imgItem = findViewById(R.id.imgitemView);
        try{



        }catch (Exception e){

        }




    }
}