package com.iqcollections;

/*
    Code Attribution 1:
    Source: YouTube
    Source URL link: https://www.youtube.com/watch?v=x0X19LJJGjc
    Title Page/Video: [Android Studio] TableLayout - Border & Collapse
    Author name/tag/channel: Coding with Sara
    Author channel/profile url link: https://www.youtube.com/c/Codingwithsara
 */

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