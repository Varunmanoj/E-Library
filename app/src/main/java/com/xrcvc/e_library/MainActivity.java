package com.xrcvc.e_library;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //    Navigation Menu Items
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //    Mai Web Page
    WebView webView;
    WebSettings webSettings;



    private void webpageloadcontent(String url) {

//                Load webpage in the app and not in an external web browser
        webView.setWebViewClient(new WebViewClient());

//        Turn Javascript on in the web page
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (CheckInternent()) {
            webView.loadUrl(url);
        }
        else {
            startActivity(new Intent(this,NoInternent.class));
        }
    }



    public boolean CheckInternent() {
        try {

//            Use the Connectivity Manager Class to get Network Info from Android device
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;
//            Check if the device is connected to internet through Wi-Fi or Mobile Network
            if (connectivityManager != null) {
                info = connectivityManager.getActiveNetworkInfo();
            }
            return info != null && info.isConnected();
        } catch (NullPointerException e) {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Link XML and Java

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigation_view);
        webView = findViewById(R.id.webView);


//        Toggle Button for Navigation
//        Create the Toggle button to Show and Hide the handburger Menu
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);

        //        Link the Drawer Layout from XML witht he toggle Button in the Action Bar
        drawerLayout.addDrawerListener(toggle);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        toggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar// to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


//        Set Item listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    if (CheckInternent()){
                        startActivity(new Intent(this,MainActivity.class));
                    }
                    else {
                        startActivity(new Intent(this,NoInternent.class));
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.latest_arrival:
                    Toast.makeText(MainActivity.this, "latest arrivals", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.all_categories:
                    Toast.makeText(MainActivity.this, "All Categories", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case  R.id.exit:
//                    Finish is used to quit the app
                    finish();
            }


            return true;
        });

        if (!CheckInternent()){
            startActivity(new Intent(this,NoInternent.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        webpageloadcontent("https://xrcvc-e-library.varunmanojkumar.in/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webpageloadcontent("https://xrcvc-e-library.varunmanojkumar.in/");
    }
}
