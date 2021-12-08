package com.example.cp470groupproject;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cp470groupproject.Adapter.ToDoAdapter;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoAdapter adapter;



    public TouchHelper(ToDoAdapter adapter) {
        super(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setMessage("Are you sure?")
                    .setTitle("Delete Task")
                    .setTitle("Delete Task")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            adapter.deleteTask(position);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    adapter.notifyItemChanged(position);

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }else{
            adapter.editTask(position);
        }
    }
}