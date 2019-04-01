package com.example.gradplanproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A RecyclerView.Adapter class must be overridden by any program that makes use of the
 * RecyclerView. The main purpose of this class is to define how each object in the RecyclerView
 * will look and determine what will (or won't) happen when the user clicks on an item.
 */

public class RecyclerAdapterCV extends RecyclerView.Adapter<RecyclerAdapterCV.ViewHolder> {

    private List<Map<String, String>> values;
    private List<Course> list;
    private WeakReference<Activity> weakRef;

    /**
     * The ViewHolder class represents each item in the RecyclerView's list.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        //public TextView courseCode;
        public TextView courseName;
        public TextView courseDetails;
        public ImageButton removeButton;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            courseName = (TextView) v.findViewById(R.id.courseName);
            courseDetails = (TextView) v.findViewById(R.id.courseDetails);
            removeButton = v.findViewById(R.id.removeButton);
        }
    }

    // Provided by the tutorial

    /*
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }
    */

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }


    public RecyclerAdapterCV(List<Course> courseList, WeakReference<Activity> wr) {
        list = courseList;
        weakRef = wr;
    }


    /**
     * LayoutManager will call this function once for every item in the provided container, using
     * the layout we provide to format each View object.
     * @param parent The group the new View will be a part of.
     * @param viewType The type of View to be generated.
     * @return The newly generated ViewHolder
     */

    @Override
    public RecyclerAdapterCV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.course_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    /**
     * LayoutManager uses this to set the data for each ViewHolder. This is called automatically
     * each item in the implemented container.
     * @param holder Specifies the ViewHolder currently being managed.
     * @param position The "index" variable that LayoutManager uses to iterate through the
     *                 container.
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Course c = list.get(position);

        String firstLine = c.getCourse() + " - " + c.getName();
        String secondLine = "Section " + c.getSection() + ": " + c.getInstructors().get(0).get("first")
                + " - ";

        ArrayList daysList = (ArrayList)c.getSchedules().get(0).get("days");
        for (int i = 0; i <= 5; i++)
            secondLine += daysList.get(i);

        if ( c.getSchedules().size() == 2 ) {
            daysList = (ArrayList)c.getSchedules().get(1).get("days");
            for (int i = 0; i <= 5; i++)
                secondLine += daysList.get(i);
        }

        secondLine += " - " + c.getSchedules().get(0).get("time");

        //holder.courseCode.setText(values.get(position).get("code"));
        holder.courseName.setText(firstLine);
        holder.courseDetails.setText(secondLine);

        /**
         * An onClickListener that applies to each individual object in the RecyclerView
         */

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weakRef.get().getApplicationContext(),
                        "Removing Item #" + (position + 1) + "...", Toast.LENGTH_SHORT).show();
                remove(position);
            }
        });
    }


    /**
     * LayoutManager calls this function to know how many ViewHolders to set.
     * @return Size of the implemented container.
     */

    @Override
    public int getItemCount() {
        return list.size();
    }
}
