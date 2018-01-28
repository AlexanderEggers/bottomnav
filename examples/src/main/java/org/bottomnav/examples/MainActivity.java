package org.bottomnav.examples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.bottomnav.BottomNavMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        BottomNavMenu bottomNavMenu = findViewById(R.id.bottom_nav);
        bottomNavMenu.setItems(menu);

        return super.onCreateOptionsMenu(menu);
    }
}
