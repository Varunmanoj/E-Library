package com.xrcvc.e_library;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

public class LatestArrival extends AppCompatActivity {
    //    Navigation Menu Items
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //    Mai Web Page
    WebView webView;
    WebSettings webSettings;

    //    WebUrl
    String WebURl="https://xrcvc-e-library.varunmanojkumar.in/latest-arrivals/";
    ProgressBar progressBar;

    FirebaseAnalytics firebaseAnalytics;

    private void webpageloadcontent(String url) {

//                Load webpage in the app and not in an external web browser
        webView.setWebViewClient(new WebViewClient() {
            //            Show Error Page in case of any error
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                startActivity(new Intent(LatestArrival.this,NoInternent.class));
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // Retrieve the page title from the Web View
                String pageTitle = view.getTitle();

                // Set the title of the action bar
                getSupportActionBar().setTitle(pageTitle);
            }

        });



//        Show a Progress Bar indicating the loading Status of the Website till the Website is fully loaded
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
//                Show Progressbar till the loading has not reached 100%
                if (progress<100){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
//                    Hide the Progress bar once the page is fully loaded
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


//        Turn Javascript on in the web page
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (CheckInternent()) {
            webView.loadUrl(url);
        } else {
            startActivity(new Intent(this, NoInternent.class));
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
        setContentView(R.layout.activity_latest_arrival);

        //        Link XML and Java

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigation_view);
        webView = findViewById(R.id.webView);
        progressBar=findViewById(R.id.progressBar);
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);

//        Toggle Button for Navigation
//        Create the Toggle button to Show and Hide the handburger Menu
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);

        //        Link the Drawer Layout from XML with he toggle Button in the Action Bar
        drawerLayout.addDrawerListener(toggle);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        toggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar// to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


//        Set Item listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
//                Check which menu item is clicked and then open the corresponding activity depending on wheather Internet Connection is present or not
                case R.id.home:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
//                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.latest_arrival:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, LatestArrival.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
//                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.all_categories:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, AllCategory.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
                    //                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.dashboard:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, AccountDashboard.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
                    //                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.help:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, help.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
                    //                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.privacy:
                    if (CheckInternent()) {
                        startActivity(new Intent(this, PrivacyPolicy.class));
                    } else {
                        startActivity(new Intent(this, NoInternent.class));
                    }
                    //                    Close the Navigation Drawer once a particular item is clicked
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }


            return true;
        });

        if (!CheckInternent()) {
            startActivity(new Intent(this, NoInternent.class));
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
        webpageloadcontent(WebURl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webpageloadcontent(WebURl);
    }
}
