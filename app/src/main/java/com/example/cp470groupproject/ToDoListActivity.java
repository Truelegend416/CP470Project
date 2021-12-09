package com.example.cp470groupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cp470groupproject.Adapter.ToDoAdapter;
import com.example.cp470groupproject.Model.ToDoModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

public class ToDoListActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView recyclerView;
    private FloatingActionButton Fab;
    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    private List<ToDoModel> List_T;
    private Query query;
    private ListenerRegistration listenerRegistration;
    GoogleSignInAccount acct;
    ImageView img;
    Uri personPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        recyclerView = findViewById(R.id.recyclerlview);
        Fab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this));

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);

            }
        });

        List_T = new ArrayList<>();

        adapter = new ToDoAdapter(ToDoListActivity.this , List_T);
        Data();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

    private void Data(){
        query = firestore.collection("task").orderBy("time", Query.Direction.DESCENDING);

        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentChange documentChange : value.getDocumentChanges()){

                    if (documentChange.getType() == DocumentChange.Type.ADDED){

                        String id = documentChange.getDocument().getId();
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                        List_T.add(toDoModel);
                        adapter.notifyDataSetChanged();

                    }
                }

                listenerRegistration.remove();


            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menutodo, m );
        MenuItem menuItem = m.findItem(R.id.profile_image);
        View view = MenuItemCompat.getActionView(menuItem);

        img = view.findViewById(R.id.photo1);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
        Glide.with(ToDoListActivity.this).load(personPhoto).into(img);

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
                Intent intent = new Intent(ToDoListActivity.this, HabitTrackerActivity.class);
                startActivity(intent);
                break;

            case R.id.home:
                Log.d("Toolbar", "ToDo Selected");
                Snackbar.make(findViewById(R.id.home), "You selected Home Page option", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent2 = new Intent(ToDoListActivity.this, MainActivity.class);
                startActivity(intent2);
                break;



            case R.id.info:
                Log.d("Toolbar", "Info Selected");
                Toast toast = Toast.makeText(this, "Version 1.0, by Mohammad Bilal Akhtar", Toast.LENGTH_LONG);
                toast.show();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(ToDoListActivity.this);
                builder2.setTitle(R.string.custom_dialogtext);

                //get Inflate and set the layout for dialog
                //Assigning the new custom dialog xml to the activity you want it to have
                //
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.custom_dialogboxtodo, null);
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


    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        List_T.clear();
        Data();
        adapter.notifyDataSetChanged();

    }
    public class AsyncTaskRunner extends AsyncTask<Void,Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                acct = GoogleSignIn.getLastSignedInAccount(ToDoListActivity.this);
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    personPhoto = acct.getPhotoUrl();



                    //Glide.with(MainActivity.this).load(personPhoto).into(img);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}