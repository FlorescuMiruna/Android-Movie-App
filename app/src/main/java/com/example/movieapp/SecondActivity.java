package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    BottomNavigationView bottomNavigationView;

//    List<String> moviesList;

//    List<Movie> movies;


    ArrayList<Movie> movies;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println("ce");
        System.out.println("extraa"+ movies);

// Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
// Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView2);
        // textView.setText(getString(R.string.hello) + message);

        movies = this.getIntent().getParcelableArrayListExtra("extra");
        System.out.println("extraa"+ movies);
        Log.d("myTag", "This is my message");
        movies.add(new Movie("Batmann", "12-03-2022","id_batman", "Nolan"));
//        movies.add(new Movie("Superman", "12-03-2022","id_batman", "Nolan"));



        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(movies);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        goToMainActivity();

                        return true;
                    case R.id.movies:

                        return true;
                    case R.id.settings:
                        return true;
                }

                return false;
            }
        });
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);

        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_manu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }




}