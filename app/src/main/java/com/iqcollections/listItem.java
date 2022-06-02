package com.iqcollections;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class listItem extends AppCompatActivity {
   private DatabaseReference dbref ;

    private ListView listview;
    private TextView txtCol;
    private String currentCol;
    private String currentGoal;
    private  String goal;
    private double precentage;
    private ArrayList<String> arrayListName = new ArrayList<>();
    private ArrayList<String> arrayListDescription= new ArrayList<>();
    private ArrayList<String> arrayListIMG = new ArrayList<>();
    private ArrayList<String> arrayListDate = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    private FirebaseUser uid;
    private static String  selectedItem;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        uid = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Items").child(uid.getUid());
        txtCol = findViewById(R.id.txtItemCollection);
        listview = (ListView) findViewById(R.id.lstItemsview);

        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item,arrayListName);
        listview.setAdapter(adapter);
        Intent intent = getIntent();

        currentCol = intent.getStringExtra("currentcolName");
        currentGoal = intent.getStringExtra("colgoal");
        counter=0;
        double curgoal = Double.parseDouble(currentGoal);
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                readItems value = snapshot.getValue(readItems.class);
            if(value != null){
                if(value.getItemCollection().equals(currentCol)){
                    arrayListName.add(value.getItemName());
                    arrayListDescription.add(value.getItemDescription());
                    arrayListDate.add(value.getItemDate());
                    arrayListIMG.add(value.getItemImage());
                    counter++;
                    precentage = (counter/curgoal)*100;
                    String itemscol = "Items avalable for  "+currentCol+":  Goal progress: "+counter+"/"+currentGoal+"  "+precentage+"%" ;
                    txtCol.setText(itemscol);
                }


                adapter.notifyDataSetChanged();}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           Intent itemIntent = new Intent(listItem.this,itemDetail.class);
            itemIntent.putExtra("itemName",arrayListName.get(i));
            itemIntent.putExtra("itemDescription",arrayListDescription.get(i));
            itemIntent.putExtra("itemDate",arrayListDate.get(i));
            itemIntent.putExtra("itemIMG",arrayListIMG.get(i));

            startActivity(itemIntent);


          }
      });

    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    //do not delete this is for the options menu buttons
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_items, menu);
        return true;
    }

    //do not delete this is for the options menu buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_create:
                Intent intent = new Intent(listItem.this, createItem.class);

                intent.putExtra("colName",currentCol);

                startActivity(intent);
                finish();
        }


        return super.onOptionsItemSelected(item);
    }

}


