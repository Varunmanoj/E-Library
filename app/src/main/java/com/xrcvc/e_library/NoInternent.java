package com.xrcvc.e_library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

public class NoInternent extends AppCompatActivity {
    Button Gohome;

    //    Navigation Menu Items
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //    Notification Sound using Sound Pool
    SoundPool soundPool;
    int notifysound;

    FirebaseAnalytics firebaseAnalytics;

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

    public void NoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Please Connect to the Internet.");

        // Set Alert Title
        builder.setTitle("No internet !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Open Network Settings ", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            OpenNetworkSetings();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        builder.setIcon(R.drawable.wifi_off);

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void OpenNetworkSetings() {
//        OPen Android Network Settings
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }


    public void createsoundpool() {
//        Create the SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//        Usage value to use when the usage is sonification, such as with user interface sounds.
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
//        Content type value to use when the content type is a sound used to accompany a user action, such as a beep or sound effect expressing a key click, or event, such as the type of a sound for a bonus being received in a game. These sounds are mostly synthesized or short Foley sounds.
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
//                Set the Maximum number of audio files can be loaded in memory and played
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

//        Create sounds Pool object with Audio Attributes object and load the Wav File in memory. Call the audio file from the raw folder using it's id.
//        Set the priority of the audio file for compatibility with future versions of Android.
        notifysound = soundPool.load(this, R.raw.windowsnotify, 1);
    }

    public void PlayNotifySound() {
//        Load the audio file , set the volume for the left , and right channel of audio on the speaker, the priority, weather to loop or not and the rate at which the audio file should be played.
        soundPool.play(notifysound, 1, 1, 0, 0, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internent);

        firebaseAnalytics= FirebaseAnalytics.getInstance(this);

        Gohome = findViewById(R.id.gohome);
        Gohome.setOnClickListener(v -> {
            if (CheckInternent()) {
                startActivity(new Intent(NoInternent.this, MainActivity.class));
            } else {
                PlayNotifySound();
                NoInternetDialog();
            }
        });

        //        Create and Load the Sound for Notifications
        createsoundpool();
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigation_view);


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
                        case R.id.all_books:
                            if (CheckInternent()) {
                                startActivity(new Intent(this,All_Books.class));
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
                        case R.id.new_book_request:
                            if (CheckInternent()) {
                                startActivity(new Intent(this, New_book_Request.class));
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
                }
        );

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
        } else if (CheckInternent()) {
            startActivity(new Intent(NoInternent.this, MainActivity.class));
        } else {
            PlayNotifySound();
            NoInternetDialog();
        }
    }
}
