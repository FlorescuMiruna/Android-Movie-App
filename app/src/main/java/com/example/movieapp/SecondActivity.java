package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
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

    List<String> moviesList;

    List<Movie> movies;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
// Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
// Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView2);
        // textView.setText(getString(R.string.hello) + message);

        movies = new ArrayList<>();

        movies.add(new Movie("Batman", "12-03-2022","id_batman", "Nolan"));
        movies.add(new Movie("Superman", "12-03-2022","id_batman", "Nolan"));

        moviesList = new ArrayList<>();
        moviesList.add("The prestige");
        moviesList.add("Iron Man");
        moviesList.add("The Incredible Hulk");
        moviesList.add("Iron Man 2");
        moviesList.add("Thor");
        moviesList.add("Captain America: The First Avenger");
        moviesList.add("The Avengers");
        moviesList.add("Iron Man 3");
        moviesList.add("Thor: The Dark World");
        moviesList.add("Captain America: The Winter Soldier");
        moviesList.add("Guardians of the Galaxy");
        moviesList.add("Avengers: Age of Ultron");
        moviesList.add("Ant-Man");
        moviesList.add("Captain America: Civil War");
        moviesList.add("Doctor Strange");
        moviesList.add("Guardians of the Galaxy Vol. 2");
        moviesList.add("Spider-Man: Homecoming");
        moviesList.add("Thor: Ragnarok");
        moviesList.add("Black Panther");
        moviesList.add("Avengers: Infinity War");
        moviesList.add("Ant-Man and the Wasp");
        moviesList.add("Captain Marvel");
        moviesList.add("Avengers: Endgame");
        moviesList.add("Spider-Man: Far From Home");

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