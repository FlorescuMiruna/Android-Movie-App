package com.example.movieapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.movieapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import com.example.movieapp.databinding.*;
public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 1;

    ActivityMainBinding mainBinding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;

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

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());

//        setContentView(R.layout.activity_main);
        setContentView(mainBinding.getRoot());

        mainBinding.btnTakePicture.setOnClickListener(view -> {
            checkCameraPermissionAndOpenCamera();
        });

        imageUri = createUri();
        registerPictureLauncher();

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

    private Uri createUri(){
        File imageFile = new File(getApplicationContext().getFilesDir(),"camera_photo.jpg");
        return FileProvider.getUriForFile(getApplicationContext(), "com.example.movieapp.fileProvider", imageFile);
    }

    private void registerPictureLauncher(){
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        try {
                            if(result) {
                                mainBinding.ivUser.setImageURI(null);
                                mainBinding.ivUser.setImageURI(imageUri);
                            }
                        }catch (Exception exception){
                            exception.getStackTrace();
                        }
                    }
                }
        );
    }

    private void  checkCameraPermissionAndOpenCamera() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE );
        } else {
            takePictureLauncher.launch(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureLauncher.launch(imageUri);
            }else {
                Toast.makeText(this,"Camera permission denied, please allow permission to take picture!!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}