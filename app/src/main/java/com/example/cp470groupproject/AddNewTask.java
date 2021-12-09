package com.example.cp470groupproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "Add New Task";

    private TextView setDueDate;
    private EditText Task_Edit;
    private Button Save_Button;
    private FirebaseFirestore firestore;
    private Context context;
    private String due_Date = "";
    private String id = "";
    private String due_Date_Update;

    public static AddNewTask newInstance(){

        return new AddNewTask();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv);
        Task_Edit = view.findViewById(R.id.task_edittext);
        Save_Button= view.findViewById(R.id.save_btn);

        firestore = FirebaseFirestore.getInstance();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();

        if(bundle!=null){
            isUpdate = true;
            String task = bundle.getString("task");
            id = bundle.getString("id");
            due_Date_Update = bundle.getString("due");
            Task_Edit.setText(task);
            setDueDate.setText(due_Date_Update);

            if(task.length() > 0){
                Save_Button.setEnabled(false);
                Save_Button.setBackgroundColor(Color.GRAY);
            }


        }

        Task_Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equals("")){
                    Save_Button.setEnabled(false);
                    Save_Button.setBackgroundColor(Color.GRAY);
                }else{
                    Save_Button.setEnabled(true);
                    Save_Button.setBackgroundColor(getResources().getColor(R.color.green_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance();

                int MONTH = calender.get(calender.MONTH);
                int YEAR = calender.get(Calendar.YEAR);
                int DAY = calender.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view , int year, int month, int dayOfMonth) {

                        month = month +1;
                        setDueDate.setText(dayOfMonth + "/"+ month + "/" + year);
                        due_Date = dayOfMonth + "/" + month + "/" + year;

                    }


                }, YEAR , MONTH , DAY);

                datePickerDialog.show();

            }
        });
        boolean finalIsUpdate = isUpdate;
        Save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = Task_Edit.getText().toString();

                if (finalIsUpdate){

                    firestore.collection("task").document(id).update("task",task,"due" , due_Date);
                    Toast.makeText(context,"Task Updated" , Toast.LENGTH_SHORT).show();

                }

                else {

                    if (task.isEmpty()) {

                        Toast.makeText(context, "Empty task not allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> taskMap = new HashMap<>();

                        taskMap.put("task", task);
                        taskMap.put("due", due_Date);
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
                }

                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
