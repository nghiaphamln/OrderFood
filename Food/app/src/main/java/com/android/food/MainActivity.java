package com.android.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.android.food.manager.AccountManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);



    }
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();
    UserFragment userFragment = new UserFragment();
    AccountManagerFragment accountManagerFragment = new AccountManagerFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                return true;

            case R.id.user:
                if (AccountManager.getInstance().isLogin()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, accountManagerFragment).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();
                }
                return true;
        }
        return false;
    }
}