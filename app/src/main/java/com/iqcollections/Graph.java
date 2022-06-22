package com.iqcollections;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Graph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BarChart barGraph;
    PieChart pieChart;
    // drawer menu variables
    DrawerLayout dl;
    NavigationView nv;


    private ListView wishlistView;
    private FirebaseUser uid;

    ArrayList<listItem> listItemsAL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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

        barGraph = findViewById(R.id.barGraph);
        pieChart = findViewById(R.id.pieChart);

        //Initialize array list
        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        //ArrayList<String> barLblNames;
        //ArrayList<String> list = new ArrayList<>();//Using an arraylist to store the data from firebase

        //Use for loop for the graph data and to get the data
        for (int i = 0; i < listItemsAL.size();i++){
            String itemId = listItemsAL.get(i).getSelectedItem();
            //Initialize bar chart entry
            BarEntry barEntry = new BarEntry(i, Float.parseFloat(itemId));
            //Initialize the pie chart entry
            PieEntry pieEntry = new PieEntry(i, Float.parseFloat(itemId));
            //add the values to the arraylist
            barEntryArrayList.add(barEntry);
            pieEntryArrayList.add(pieEntry);

        }
        // Initialize the bar data set
        BarDataSet barDataSetItems = new BarDataSet(barEntryArrayList, "Collections");
        // set the colors for the bar graph
        barDataSetItems.setColors(ColorTemplate.COLORFUL_COLORS);
       //Hide draw value
        barDataSetItems.setDrawValues(false);
        //Set the bar data
        barGraph.setData((new BarData(barDataSetItems)));
        //set the animation of the graph
        barGraph.animateY(5000);
        //set the description of the graph text and color
        barGraph.getDescription().setText("Items Chart");
        barGraph.getDescription().setTextColor(Color.BLUE);

        // Initialize the bar data set
        PieDataSet pieDataSetItems = new PieDataSet(pieEntryArrayList, "Collections");
        // set the colors for the bar graph
        pieDataSetItems.setColors(ColorTemplate.COLORFUL_COLORS);
        //Hide draw value
        pieDataSetItems.setDrawValues(false);
        //Set the bar data
        pieChart.setData((new PieData(pieDataSetItems)));
        //set the animation of the graph
        pieChart.animateXY(5000 , 5000);
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