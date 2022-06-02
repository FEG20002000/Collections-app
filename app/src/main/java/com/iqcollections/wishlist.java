package com.iqcollections;

/*
    Code Attribution 1:
    Source: YouTube
    Source URL link: https://www.youtube.com/watch?v=XactTKR0Wfc
    Title Page/Video: Firebase Android Tutorial 5 - Retrieving Data from Firebase Realtime Database
    Author name/tag/channel: ProgrammingKnowledge
    Author channel/profile url link: https://www.youtube.com/c/ProgrammingKnowledge
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class wishlist extends AppCompatActivity {

    private ListView wishlistView;
    private FirebaseUser uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wishlistView = findViewById(R.id.wishlistView);
        uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user
        ArrayList<String> list = new ArrayList<>();//Using an arraylist to store the data from firebase
        ArrayAdapter adapter = new ArrayAdapter<String>(wishlist.this, R.layout.wishlist_item, list);
        wishlistView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Wishlist").child(uid.getUid());// database reference to call the data

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();// clear the list
                // using a DataSnapshot that contains data from the database location to read the data in the database
                for (DataSnapshot wishSnapshot : snapshot.getChildren()) {
                    wishClass wList = wishSnapshot.getValue(wishClass.class);// getting the value from the wishClass class
                    String txt = "Wish Item: " + wList.getWishName() +
                            " \nWish Description: " + wList.getWishDesc() +
                            " \nExpected Price: R" + wList.getWishPrice();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // calling the nav menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_wish, menu);
        return true;
    }

    //do not delete this is for the options menu buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_addwish:
                startActivity(new Intent(wishlist.this, addWish.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}