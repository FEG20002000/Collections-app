package com.iqcollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.type.Date;
import com.google.type.DateTime;

public class createItem extends AppCompatActivity {

    private EditText edtItemName,edtItemDescription;
    private View dtitemDate;
    private Button btnImageSelect,btnCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);


        edtItemDescription = findViewById(R.id.edtitemDesc);
        edtItemName = findViewById(R.id.edtItemname);
        dtitemDate = findViewById(R.id.dtpItemDate);
        btnImageSelect = findViewById(R.id.selectitemimg);
        btnCreate = findViewById(R.id.btnCreateitem);




    }


}