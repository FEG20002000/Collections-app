package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.iqcollections.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    private GoogleSignInOptions gsi;
    private GoogleSignInClient gsc;

    // drawer menu variables
    DrawerLayout dl;
    NavigationView nv;
    Toolbar tb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //binding for gridview
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hooks
        dl = findViewById(R.id.mainlayout);
        nv = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, R.string.navi_open, R.string.navi_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        nv.bringToFront();
        nv.setNavigationItemSelectedListener(this);



        //adding name of collection pull from Database to griveiw
        String[] collectionName = {"Car","IT","PC"};
       //adding images to an array must pull from database to grid view
        int[] collectionIMg = {R.drawable.background,R.drawable.background,R.drawable.background};

        grid_adapter gridAdapter = new grid_adapter(MainActivity.this, collectionName, collectionIMg) {
        };
        binding.grdMain.setAdapter(gridAdapter);

        //for old grid view
        binding.grdMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this,"You Clicked on "+ collectionName[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

    //do not delete this is for the options menu buttons
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_drawer,menu);
        return true;
    }
    //do not delete this is for the options menu buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_create: startActivity(new Intent(MainActivity.this,createCollection.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_main:
                break;
            case R.id.nav_fav:
                Intent intent = new Intent(MainActivity.this, favourite.class);
                startActivity(intent);
                break;
            case R.id.nav_wish:
                intent = new Intent(MainActivity.this, wishlist.class);
                startActivity(intent);
                break;

        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}