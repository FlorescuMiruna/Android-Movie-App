package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    BottomNavigationView bottomNavigationView;

    List<String> moviesList;

    List<Movie> movies;

//    HomeFragment homeFragment = new HomeFragment();
//    SettingsFragment settingsFragment = new SettingsFragment();
//    NotificationFragment notificationFragment = new NotificationFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = new ArrayList<>();
        moviesList.add("Inception");
        moviesList.add("Iron Man");

        movies = new ArrayList<>();

        movies.add(new Movie("Batman", "12-03-2022", String.valueOf(R.drawable.batman), "Nolan"));
        movies.add(new Movie("Superman", "12-03-2022", String.valueOf(R.drawable.batman), "Nolan"));
        movies.add(new Movie("Batman vs Superman", "12-03-2022", String.valueOf(R.drawable.batman), "Nolan"));
        movies.add(new Movie("Inception", "12-03-2022", String.valueOf(R.drawable.batman), "Nolan"));
        System.out.println(movies);

        Log.d("MOV", movies.toString());
//        moviesList.add("The Incredible Hulk");
//        moviesList.add("Iron Man 2");
//        moviesList.add("Thor");
//        moviesList.add("Captain America: The First Avenger");
//        moviesList.add("The Avengers");
//        moviesList.add("Iron Man 3");
//        moviesList.add("Thor: The Dark World");
//        moviesList.add("Captain America: The Winter Soldier");
//        moviesList.add("Guardians of the Galaxy");
//        moviesList.add("Avengers: Age of Ultron");
//        moviesList.add("Ant-Man");
//        moviesList.add("Captain America: Civil War");
//        moviesList.add("Doctor Strange");
//        moviesList.add("Guardians of the Galaxy Vol. 2");
//        moviesList.add("Spider-Man: Homecoming");
//        moviesList.add("Thor: Ragnarok");
//        moviesList.add("Black Panther");
//        moviesList.add("Avengers: Infinity War");
//        moviesList.add("Ant-Man and the Wasp");
//        moviesList.add("Captain Marvel");
//        moviesList.add("Avengers: Endgame");
//        moviesList.add("Spider-Man: Far From Home");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(movies);

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



        bottomNavigationView  = findViewById(R.id.bottom_navigation);

//        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:


                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.movies:
                        goToSecondActivity();
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;
                    case R.id.settings:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }

    private void goToSecondActivity(){
//        Intent intent = new Intent(this,SecondActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("data", (Parcelable) movies);
//        intent.putExtras(bundle);


        Intent i = new Intent(this,SecondActivity.class);

//        ArrayList<Movie> testing = new ArrayList<Movie>();

        i.putParcelableArrayListExtra("extra", (ArrayList<? extends Parcelable>) movies);

        startActivity(i);


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