package com.xrcvc.e_library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternent extends AppCompatActivity {
    Button Gohome;
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
    public  void NoInternetDialog(){
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
        startActivity (new Intent (android.provider.Settings.ACTION_WIRELESS_SETTINGS));startActivity (new Intent (android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internent);

        Gohome=findViewById(R.id.gohome);
        Gohome.setOnClickListener(v -> {
            if (CheckInternent()) {
                startActivity(new Intent(NoInternent.this, MainActivity.class));
            }
            else {
                NoInternetDialog();
            }
        });
    }
}
