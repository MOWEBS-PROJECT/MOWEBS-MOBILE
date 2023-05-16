package com.example.mowebs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigationMenu);
        frameContent = findViewById(R.id.fragmentContent);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContent, new DashboardFragment(MainActivity.this))
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContent, fragment)
                            .commit();
                }
                return true;
            }
        });

    }
}