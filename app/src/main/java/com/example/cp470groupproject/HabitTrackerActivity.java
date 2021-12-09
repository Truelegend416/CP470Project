package com.example.cp470groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.example.cp470groupproject.Adapter.ToDoAdapter;
import com.example.cp470groupproject.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HabitTrackerActivity extends AppCompatActivity {
    ArrayList<String> habitList;
    Cursor cursor;
    SQLiteDatabase db;
    FragmentContainerView fragCon;
    Habit currHabit;
    ListView habits;
    String ACTIVITY_NAME = "HabitTrackerActivity";
    FloatingActionButton Fab;
    private ArrayList<Habit> List_T;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private FirebaseFirestore firestore;
    private HabitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        Fab = findViewById(R.id.floatingActionButton);
        habits = findViewById(R.id.Habits);
        habitList = new ArrayList<String>();
        habitList.clear();
        HabitAdapter habitad = new HabitAdapter(this);
        habits.setAdapter(habitad);
        firestore = FirebaseFirestore.getInstance();
        fragCon = findViewById(R.id.fragmentContainerView);
        FragmentManager fragment = getSupportFragmentManager();
        AddHabit fragment2 = new AddHabit();

        //fragment.beginTransaction().add(R.id.fragmentContainerView, fragment2).commit();
        fragCon.setVisibility(View.INVISIBLE);

        List_T = new ArrayList<>();

        //adapter = new HabitAdapter(HabitTrackerActivity.this);
        //new HabitAdapter(HabitTrackerActivity.this , List_T);
        //Data();


        HabitDatabase databaseHelper = new HabitDatabase(this);
        db = databaseHelper.getWritableDatabase();


        cursor = db.rawQuery("SELECT * FROM " + HabitDatabase.TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            habitList.add(cursor.getString(cursor.getColumnIndexOrThrow(HabitDatabase.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndexOrThrow(HabitDatabase.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();
        }

        // Print column names
        for (int i = 0; i < cursor.getCount(); i++) {
            String column = cursor.getColumnName(0);
            System.out.println(column);
        }



        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCon.setVisibility(View.VISIBLE);
                FragmentManager fragment = getSupportFragmentManager();
                AddHabit fragment2 = new AddHabit(currHabit);

                fragment.beginTransaction().add(R.id.fragmentContainerView, fragment2).commit();

                //if (currHabit!=null){




                //fragCon.setVisibility(View.INVISIBLE);


                //ContentValues values = new ContentValues();
                //values.put(HabitDatabase.KEY_MESSAGE, currHabit.getTitle());
                //db.insert(HabitDatabase.TABLE_NAME, null, values);



            }
        });



    }
    /*
    private void Data(){
        query = firestore.collection("habit").orderBy("time", Query.Direction.DESCENDING);

        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentChange documentChange : value.getDocumentChanges()){

                    if (documentChange.getType() == DocumentChange.Type.ADDED){

                        //String id = documentChange.getDocument().getId();
                        //ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                        //List_T.add(toDoModel);
                        //adapter.notifyDataSetChanged();

                    }
                }

                listenerRegistration.remove();


            }
        });
        */


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
        class HabitAdapter extends ArrayAdapter<String> {
            public HabitAdapter(Context ctx) {
                super(ctx, 0);
            }

            public int getCount() {
                return habitList.size();
            }

            public String getItem(int position) {
                return habitList.get(position);
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = HabitTrackerActivity.this.getLayoutInflater();
                View result = null ;
                TextView Habit;
                String text;
                result = inflater.inflate(R.layout.each_habit,null);
                text = getItem(position);
                //Habit.setText(text);




                return result;
            }

            public long getItemId(int position) {
                cursor.moveToPosition(position);
                return Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(HabitDatabase.KEY_ID)));

            }
        }



}