package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class aboutDisplay extends AppCompatActivity {

    private Button btnAbtReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_display);

        btnAbtReturn = findViewById(R.id.btnAbtReturn);

        btnAbtReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(aboutDisplay.this, MainActivity.class));
                finish();
            }
        });
    }
}