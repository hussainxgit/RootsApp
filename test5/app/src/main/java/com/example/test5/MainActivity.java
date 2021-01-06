package com.example.test5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.example.test5.adapters.FragmentAdapter;
import com.example.test5.pages.ShoppingCart;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    private ImageButton shoppingCart;
    private static final String ONESIGNAL_APP_ID = "8133733f-2482-4d49-a15e-49be8cd4cb15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        ViewPager viewPager = findViewById(R.id.viewPager2);
        TabLayout tabLayout = findViewById(R.id.tableLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        shoppingCart = findViewById(R.id.shoppingCart);

        // Tab layout
        tabLayout.setupWithViewPager(viewPager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).setText("Indoor");
        tabLayout.getTabAt(1).setText("Outdoor");
        tabLayout.getTabAt(2).setText("Garden");

        // drawer layout
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("About");

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                ).build();

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                startActivity(intent);
            }
        });

    }
}