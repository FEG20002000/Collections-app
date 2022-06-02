package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class groupMembers extends AppCompatActivity {

    private Button btnGrpReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        btnGrpReturn = findViewById(R.id.btnGrpReturn);

        btnGrpReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(groupMembers.this, MainActivity.class));
                finish();
            }
        });
    }
}