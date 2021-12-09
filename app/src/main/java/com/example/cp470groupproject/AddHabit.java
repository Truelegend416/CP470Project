package com.example.cp470groupproject;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.core.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddHabit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHabit extends Fragment implements View.OnClickListener {
    Button cancel;
    Button add;
    Habit newHabit;
    String title;
    String desc;
    String date;
    EditText titleText;
    EditText description;
    EditText dateText;
    String id;
    Context context = this.getContext();
    private FirebaseFirestore firestore;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddHabit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHabit.
     */
    // TODO: Rename and change types and number of parameters
    public static AddHabit newInstance(String param1, String param2) {
        AddHabit fragment = new AddHabit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
    public AddHabit (Habit newHabit){
        this.newHabit = newHabit;

    }

    public AddHabit(AddHabit added){
        this.newHabit = added.newHabit;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }
    public void goToAttract(View v)
    {
        Intent intent = new Intent(getActivity(), HabitTrackerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == cancel) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(AddHabit.this).commit();
            //finish();
            //getFragmentManager().popBackStack();
        } else if (view == add){
            title = titleText.getText().toString();
            desc = description.getText().toString();
            date = dateText.getText().toString();
            newHabit = new Habit(title,desc,date);

            getActivity().getSupportFragmentManager().beginTransaction().remove(AddHabit.this).commit();



            /*
            firestore.collection("Habit").document(id).update("Habit",this.newHabit,"due" , this.newHabit.getReminder());
            Toast.makeText(context,"Task Updated" , Toast.LENGTH_SHORT).show();
                if (this.newHabit.isEmpty(newHabit)) {

                    Toast.makeText(context, "Empty task not allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> taskMap = new HashMap<>();

                    taskMap.put("task", newHabit);
                    taskMap.put("due", newHabit.getReminder());
                    taskMap.put("status", 0);
                    taskMap.put("time", FieldValue.serverTimestamp());
                    firestore.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Task saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                */

                //getFragmentManager().popBackStack();
            }


        }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_habit, container, false);
        cancel = view.findViewById(R.id.cancel_btn);
        add = view.findViewById(R.id.addhabit);
        Bundle bundle = getArguments();
        if (bundle != null){
            id = bundle.getString("id");
        }
        titleText = view.findViewById(R.id.habitTitle);
        description = view.findViewById(R.id.habitDesc);
        dateText = view.findViewById(R.id.editTextDate);
        firestore = FirebaseFirestore.getInstance();
        cancel.setOnClickListener(this);
        add.setOnClickListener(this);

        return view;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }


}