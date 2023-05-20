package com.example.yelpassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerInputViewAdapter extends RecyclerView.Adapter<RecyclerInputViewAdapter.RecyclerViewHolder> {

    // creating a variable for our array list and context.
    private ArrayList<RecyclerInput> courseDataArrayList;
    private Context mcontext;

    // creating a constructor class.
    public RecyclerInputViewAdapter(ArrayList<RecyclerInput> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylcler, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        RecyclerInput recyclerData = courseDataArrayList.get(position);
        holder.courseNameTV.setText(recyclerData.getTitle());
        holder.courseDescTV.setText(recyclerData.getDescription());
        holder.mai.setText(recyclerData.getMai());
        holder.tim.setText(recyclerData.getTim());
    }

    @Override
    public int getItemCount() {
        // this method returns
        // the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating a variable for our text view.
        private TextView courseNameTV;
        private TextView courseDescTV;
        private TextView tim;
        private  TextView mai;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDesc);
            tim = itemView.findViewById(R.id.disp_time);
            mai= itemView.findViewById(R.id.disp_mail);
        }
    }
}
