package com.example.mowebs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigationMenu);
        frameContent = findViewById(R.id.fragmentContent);

        if (getIntent().getStringExtra("openFragment") != null &&
                getIntent().getStringExtra("openFragment").equals("CUSTOMER_SERVICE")) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_message);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContent, new ChatFragment(MainActivity.this))
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContent, new DashboardFragment(MainActivity.this))
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new DashboardFragment(MainActivity.this);
                        break;
                    case R.id.navigation_search:
                        fragment = new ExplorerFragment(MainActivity.this);
                        break;
                    case R.id.navigation_message:
                        fragment = new ChatFragment(MainActivity.this);
                        break;
                    case R.id.nagivation_profile:
                        fragment = new ProfileFragment(MainActivity.this);
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContent, fragment)
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                return true;
            }
        });

    }
}