package com.groovylamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;

//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;

import com.groovylamp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
//    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("ALARM_TRIGGERED".equals(intent.getAction())) {
//                Toast.makeText(context, "Событие по истечении времени!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        IntentFilter filter = new IntentFilter("ALARM_TRIGGERED");
//        registerReceiver(alarmReceiver, filter);
//        FileInputStream serviceAccount =
//                new FileInputStream("path/to/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();

//        FirebaseApp.initializeApp(options);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_profile) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_settingsFragment_to_profileFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void OnCickOpenTimer(View view) {
        Intent intent= new Intent(this , Timer.class);
        startActivity(intent);
    }

    public void OnClickOpenAlarm(View view) {
        Intent intent = new Intent(this , Alarm.class);
        startActivity(intent);
    }

    public void OnClickOpenEffects(View view) {
        Intent intent = new Intent(this, Effects.class);
        startActivity(intent);
    }

//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(alarmReceiver);
//        super.onDestroy();
//    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}