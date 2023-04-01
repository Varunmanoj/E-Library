package com.xrcvc.e_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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

//    Firebase

    private void webpageloadcontent(String url) {

//                Load webpage in the app and not in an external web browser
        webView.setWebViewClient(new WebViewClient());

//        Turn Javascript on in the web page
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (CheckInternent()) {
            webView.loadUrl(url);
        }
    }


    public boolean CheckInternent() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;
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

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigation_view);
        webView = findViewById(R.id.webView);


//        Toggle Button for Navigation
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar// to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


//        Set Item listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Toast.makeText(MainActivity.this, "Click on Home", Toast.LENGTH_SHORT).show();
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
            }


            return true;
        });

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
}
