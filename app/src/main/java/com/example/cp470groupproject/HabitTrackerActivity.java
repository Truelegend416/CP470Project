package com.example.cp470groupproject;

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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class HabitTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menuhabit, m );
        return true;
    }

    //the items you want to be in the toolbar,
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        int id = mi.getItemId();
        switch(id)
        {

            case R.id.home:
                Log.d("Toolbar", "Habit Tracker Selected");
                Snackbar.make(findViewById(R.id.home), "You selected Home Page option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(HabitTrackerActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.todotracker:
                Log.d("Toolbar", "ToDo Selected");
                Snackbar.make(findViewById(R.id.todotracker), "You selected ToDo Tracker option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent2 = new Intent(HabitTrackerActivity.this, ToDoListActivity.class);
                startActivity(intent2);
                break;
            case R.id.statstracker:
                Log.d("Toolbar", "Stats Selected");
                Snackbar.make(findViewById(R.id.statstracker), "You selected Stats Tracker option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent3 = new Intent(HabitTrackerActivity.this, StatisticsActivity.class);
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

                AlertDialog.Builder builder2 = new AlertDialog.Builder(HabitTrackerActivity.this);
                builder2.setTitle(R.string.custom_dialogtext);

                //get Inflate and set the layout for dialog
                //Assigning the new custom dialog xml to the activity you want it to have
                //
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.custom_dialogboxhabit, null);
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