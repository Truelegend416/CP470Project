package com.example.cp470groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    String settingText = "";
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    GoogleSignInClient gsic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button signout_Button = findViewById(R.id.signout_Button);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsic = GoogleSignIn.getClient(this, gso);

        signout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        final Button habitTracker = findViewById(R.id.habitTracker_button);
        habitTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HabitTrackerActivity.class);
                startActivity(intent);
            }
        });

        final Button toDoList = findViewById(R.id.toDoList_button);
        toDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
                startActivity(intent);
            }
        });

        final Button statisticsButton = findViewById(R.id.statistics_button);
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Welcome to this Lovely Project", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private void signOut()
    {
        gsic.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    //the items you want to be in the toolbar,
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        int id = mi.getItemId();
        switch(id)
        {

            case R.id.habittracker:
                Log.d("Toolbar", "Habit Tracker Selected");
                Snackbar.make(findViewById(R.id.habittracker), "You selected Habit Tracker option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, HabitTrackerActivity.class);
                startActivity(intent);
                break;

            case R.id.todotracker:
                Log.d("Toolbar", "ToDo Selected");
                Snackbar.make(findViewById(R.id.todotracker), "You selected ToDo Tracker option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent2 = new Intent(MainActivity.this, ToDoListActivity.class);
                startActivity(intent2);
                break;
            case R.id.statstracker:
                Log.d("Toolbar", "Stats Selected");
                Snackbar.make(findViewById(R.id.statstracker), "You selected Stats Tracker option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent3 = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent3);
                break;

            case R.id.signout:
                Log.d("Toolbar", "Sign out Selected");
                //need to figure out how to add the signout function here
                break;


            case R.id.info:
                Log.d("Toolbar", "Info Selected");
                Toast toast = Toast.makeText(this, "Version 1.0, by Mohammad Bilal Akhtar", Toast.LENGTH_LONG);
                toast.show();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle(R.string.custom_dialogtext);

                //get Inflate and set the layout for dialog
                //Assigning the new custom dialog xml to the activity you want it to have
                //
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.custom_dialogbox, null);
                builder2.setView(dialoglayout);
// Add the buttons
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                    }
                });
//                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
// Create the AlertDialog
                AlertDialog dialog2 = builder2.create();
                dialog2.show();

        }
        return false;
    }
}