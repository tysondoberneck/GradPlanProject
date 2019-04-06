package com.example.gradplanproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A RecyclerView.Adapter class must be overridden by any program that makes use of the
 * RecyclerView. The main purpose of this class is to define how each object in the RecyclerView
 * will look and determine what will (or won't) happen when the user clicks on an item.
 */

public class RecyclerAdapterCV extends RecyclerView.Adapter<RecyclerAdapterCV.ViewHolder> {

    private List<Course> list;
    private WeakReference<Activity> weakRef;


    /**
     * The ViewHolder class represents each item in the RecyclerView's list.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView courseDetails;
        TextView courseDetails2;
        ImageButton removeButton;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;

            courseName = v.findViewById(R.id.courseName);
            courseDetails = v.findViewById(R.id.courseDetails);
            courseDetails2 = v.findViewById(R.id.courseDetails2);
            removeButton = v.findViewById(R.id.removeButton);
        }
    }


    // This method was provided by the tutorial used to make this RecyclerAdapter.

    /*
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }
    */

    /**
     * Called by the onClickListener given to the remove button in each ViewHolder. Removes the
     * Course from the list and notifies the Adapter that the list of items has changed.
     * @param position Index used to specify which Course is being removed.
     */

    private void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }


    RecyclerAdapterCV(List<Course> courseList, WeakReference<Activity> wr) {
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
    @NonNull
    public RecyclerAdapterCV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.course_view_layout, parent, false);

        return new ViewHolder(v);
    }


    /**
     * LayoutManager uses this to set the data for each ViewHolder. This is called automatically
     * for each item in the implemented container.
     * @param holder Specifies the ViewHolder currently being managed.
     * @param position The index variable that LayoutManager uses to iterate through the
     *                 container.
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Course c = list.get(position);

        String firstLine;
        String secondLine;
        String thirdLine;

        /*
         * This next series of if-statements determines what text goes into each item in the RecyclerAdapter.
         *
         * NO RESULTS is the "code" given to the Course that is created when a search returns no results.
         * secondLine and thirdLine are left blank to make it look like a message.
         *
         * "ONLN" goes in the "type" field for all online courses. In this case, the days and time do
         * not need to be filled.
         *
         * The final case will apply to most courses, filling the View with the Course's code, name,
         * instructor, days, time, etc.
         */

        if( c.getType().equals("ONLN") ) {

            firstLine = c.getCode() + " - " + c.getName();

            if (c.getInstructors().size() == 0) {
                secondLine = "None" + " - ";
            } else {
                secondLine = c.getInstructors().get(0).get("first") + ", "
                        + c.getInstructors().get(0).get("last") + " - ";
            }

            secondLine += "Online Course";

            thirdLine = "Credits: " + c.getCredits() + " - " + c.getSeatsFilled() + "/"
                + c.getSeatsTotal() + " Seats Filled";
        }
        else {

            firstLine = c.getCode() + " - " + c.getName();

            if (c.getInstructors().size() == 0) {
                secondLine = "None" + " - ";
            } else {
                secondLine = c.getInstructors().get(0).get("first") + ", "
                        + c.getInstructors().get(0).get("last") + " - ";
            }

            List<String> daysList = c.getSingleDaysArray();
            for (int i = 0; i <= 5; i++)
                secondLine += daysList.get(i);

            secondLine += " - " + c.getSchedules().get(0).get("time");

            thirdLine = "Credits: " + c.getCredits() + " - " + c.getSeatsFilled() + "/"
                    + c.getSeatsTotal() + " Seats Filled";
        }

        holder.courseName.setText(firstLine);
        holder.courseDetails.setText(secondLine);
        holder.courseDetails2.setText(thirdLine);


        // An onClickListener attached to the button provided in each View.
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weakRef.get().getApplicationContext(),
                        "Removing course...", Toast.LENGTH_LONG).show();
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
