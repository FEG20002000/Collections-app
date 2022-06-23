package com.iqcollections;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Graph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    PieChart pieChart;
    // drawer menu variables
    DrawerLayout dl;
    NavigationView nv;


    private ListView wishlistView;
    private FirebaseUser uid;
    private DatabaseReference db, dbref;


    ArrayList<listItem> listItemsAL = new ArrayList<>();
    ArrayList<readColGraph> readColGraphs = new ArrayList<>();
    ArrayList<Float> colNumbers = new ArrayList<Float>();
    ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
    ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        pieChart = findViewById(R.id.pieChart);

        // hooks
        dl = findViewById(R.id.graphLayout);
        nv = findViewById(R.id.nav_view);
        //nav menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, R.string.navi_open, R.string.navi_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        nv.bringToFront();
        nv.setNavigationItemSelectedListener(this);

        uid = FirebaseAuth.getInstance().getCurrentUser();// setting the main user
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Wishlist").child(uid.getUid());// database reference to call the data

        // Graph starts from here


        //Initialize array list

        //ArrayList<String> barLblNames;
        //ArrayList<String> list = new ArrayList<>();//Using an arraylist to store the data from firebase
        //getting items from database

        db = FirebaseDatabase.getInstance().getReference("Collections").child(uid.getUid());
        dbref = FirebaseDatabase.getInstance().getReference("Items").child(uid.getUid());


        db.addChildEventListener(new ChildEventListener() {//searching though data
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                readColGraph collections = snapshot.getValue(readColGraph.class);
                readColGraphs.add(collections);

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
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countItems();//once data is stored counting items
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void countItems() {
        for (int i = 0; i < readColGraphs.size(); i++) {//counting items for each collection
            int e = i;
            final int[] counter = {0};
            dbref.addChildEventListener(new ChildEventListener() {//checking each item
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    readItemGraph value = snapshot.getValue(readItemGraph.class);

                    if (readColGraphs.get(e).getColName().equals(value.getItemCollection())) {
                        counter[0]++;
                    }

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

            int f = i;
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    colNumbers.add(f, (float) counter[0]);//adding number of items
                    if (colNumbers.size() >= readColGraphs.size()) {
                        setPieChart();//setting pie chart only if all counts are done


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


    public void setPieChart() {
        pieChart.clear();
        //Use for loop for the graph data and to get the data
        for (int i = 0; i < readColGraphs.size(); i++) {
            String itemId = readColGraphs.get(i).getColName();//data
            Float itemAmounts = colNumbers.get(i);//data

            //Initialize bar chart entry
            //BarEntry  barEntry = new BarEntry(itemAmounts, Float.parseFloat(itemId));

            //Initialize the pie chart entry
            PieEntry pieEntry = new PieEntry(itemAmounts, itemId);
            //add the values to the arraylist
            // barEntryArrayList.add(barEntry);

            pieEntryArrayList.add(new PieEntry(itemAmounts, itemId));

        }


        // Initialize the bar data set
        PieDataSet pieDataSetItems = new PieDataSet(pieEntryArrayList, "Collections");
        // set the colors for the bar graph
        pieDataSetItems.setColors(ColorTemplate.COLORFUL_COLORS);
        //Hide draw value
        //pieDataSetItems.setDrawValues(true);
        //Set the bar data
        pieChart.setData((new PieData(pieDataSetItems)));
        //set the animation of the graph
        pieChart.animateY(5000);
        //set the description of the graph text and color
        pieChart.getDescription().setText("Items Chart");
        pieChart.getDescription().setTextColor(Color.BLUE);


    }


    //do not delete this is for the options menu buttons
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_drawer, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wish:
                intent = new Intent(this, wishlist.class);
                startActivity(intent);

                break;
            case R.id.nav_member:
                intent = new Intent(this, groupMembers.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(this, aboutDisplay.class);
                startActivity(intent);
                break;
            case R.id.nav_graph:
                intent = new Intent(this, Graph.class);
                startActivity(intent);
                break;

        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}