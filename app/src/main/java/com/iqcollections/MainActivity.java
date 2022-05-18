package com.iqcollections;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iqcollections.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //adding name of collection pull from Database
        String[] collectionName = {"Car","IT","PC"};
       //adding images to an array must pull from database
        int[] collectionIMg = {R.drawable.background,R.drawable.background,R.drawable.background};

        grid_adapter gridAdapter = new grid_adapter(MainActivity.this, collectionName, collectionIMg) {
        };
        binding.grdMain.setAdapter(gridAdapter);


        binding.grdMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this,"You Clicked on "+ collectionName[position],Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_drawer,menu);
        return true;
    }
}