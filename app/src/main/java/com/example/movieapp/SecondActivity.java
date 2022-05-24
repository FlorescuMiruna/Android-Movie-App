package com.example.movieapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter2 recyclerAdapter;
    BottomNavigationView bottomNavigationView;



//    List<String> moviesList;

//    List<Movie> movies;


    ArrayList<Movie> movies;
    ArrayList<Movie> reservedMovies = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            System.out.println("AICI");
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
// Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


        movies = this.getIntent().getParcelableArrayListExtra("extra");








     for(Movie movie : movies){
         if(movie.getReserved())
             reservedMovies.add(movie);
     }


        System.out.println("reservedMovies"+ reservedMovies.toString());
//        movies.add(new Movie("Superman", "12-03-2022","id_batman", "Nolan"));



        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter2(reservedMovies);
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
                    case R.id.notification:
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(SecondActivity.this,"My notification");

                        builder.setContentTitle("Movie Notification");
                        builder.setContentText("Grab your tikets now!");
                        builder.setSmallIcon(R.drawable.ic_baseline_movie_creation_24);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SecondActivity.this);
                        managerCompat.notify(1,builder.build());
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