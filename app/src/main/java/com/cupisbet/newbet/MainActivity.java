package com.cupisbet.newbet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.cupisbet.newbet.fragment.ArticlesFragment;
import com.cupisbet.newbet.fragment.HomeFragment;
import com.cupisbet.newbet.fragment.MatchFragment;
import com.cupisbet.newbet.fragment.OddsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    HomeFragment homeFragment = new HomeFragment();
    MatchFragment matchFragment = new MatchFragment();
    ArticlesFragment articlesFragment = new ArticlesFragment();
    OddsFragment OddsFragment = new OddsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.match:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, matchFragment).commit();
                return true;

            case R.id.articles:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, articlesFragment).commit();
                return true;

            case R.id.odds:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, OddsFragment).commit();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}