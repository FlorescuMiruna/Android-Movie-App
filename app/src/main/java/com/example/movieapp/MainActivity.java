package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    BottomNavigationView bottomNavigationView;



    List<Movie> movies;

//    HomeFragment homeFragment = new HomeFragment();
//    SettingsFragment settingsFragment = new SettingsFragment();
//    NotificationFragment notificationFragment = new NotificationFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = getIntent().getData();
        if(uri != null){
            List<String> params = uri.getPathSegments();
            String id = params.get(params.size() - 1);
            Toast.makeText(this, "id" + id, Toast.LENGTH_SHORT).show();

        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            System.out.println("AICI");
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }



        movies = new ArrayList<>();

        movies.add(new Movie("Batman", "12-06-2022", "batman", "\n" + "Matt Reeves",false));
        movies.add(new Movie("Superman", "12-03-2022", "superman", "Zack Snyder",false));
        movies.add(new Movie("Dr. Strange", "12-03-2022", "strange", "\n" + "Sam Raimi",false));
        movies.add(new Movie("Thor", "08-07-2022", "thor", "\n" + "Taika Waititi",false));
        movies.add(new Movie("Inception", "11-03-2022","inception", "Christopher Nolan",false));
        movies.add(new Movie("Tenet", "12-03-2022", "tenet", "Christopher Nolan",false));
        movies.add(new Movie("Interstellar", "9-07-2022", "interstellar", "Christopher Nolan",false));
        movies.add(new Movie("Don't loop up", "12-03-2022","up", "Adam McKay",false));

        System.out.println(movies);

        Log.d("MOV", movies.toString());

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

                        return true;
                    case R.id.movies:
                        goToSecondActivity();
                        return true;
                    case R.id.notification:
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"My notification");

                        builder.setContentTitle("Movie Notification");
                        builder.setContentText("Grab your tikets now!");
                        builder.setSmallIcon(R.drawable.ic_baseline_movie_creation_24);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                        managerCompat.notify(1,builder.build());
                      //  getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
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