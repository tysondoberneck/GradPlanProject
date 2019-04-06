package com.example.gradplanproject;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A RecyclerView.Adapter class must be overridden by any program that makes use of the
 * RecyclerView. The main purpose of this class is to define how each object in the RecyclerView
 * will look and determine what will (or won't) happen when the user clicks on an item.
 */

public class RecyclerAdapterS extends RecyclerView.Adapter<RecyclerAdapterS.ViewHolder> {

    private List<Course> list;
    private WeakReference<Activity> weakRef;

    /**
     * The ViewHolder class represents each item in the RecyclerView's list.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView courseDetails;
        TextView courseDetails2;
        ImageButton addButton;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;

            courseName = v.findViewById(R.id.courseName);
            courseDetails = v.findViewById(R.id.courseDetails);
            courseDetails2 = v.findViewById(R.id.courseDetails2);
            addButton = v.findViewById(R.id.addButton);
        }
    }

    // These methods were provided by the tutorial used to make this RecyclerAdapter.

    /*
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    */

    RecyclerAdapterS(List<Course> courseList, WeakReference<Activity> wr) {
        list = courseList;
        weakRef = wr;
    }


    /**
     * LayoutManager will call this function once for every item in the provided container, using
     * the given layout to format each View object.
     * @param parent The group the new View will be a part of.
     * @param viewType The type of View to be generated.
     * @return The newly generated ViewHolder
     */

    @Override
    public RecyclerAdapterS.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.search_layout, parent, false);

        return (new ViewHolder(v));
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

        String firstLine;
        String secondLine;
        String thirdLine;

        /**
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

        if ( c.getCode().equals("NO RESULTS") ) {
            firstLine = c.getCode() + " - " + c.getName();
            secondLine = "";
            thirdLine = "";
        }
        else if ( c.getType().equals("ONLN") ) {

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

        /**
         * An onClickListener attached to the button provided in each View.
         */

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( list.get(position).getCode().equals("NO RESULTS") ) {
                    Toast.makeText(weakRef.get().getApplicationContext(),
                            "You can't save this course!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(weakRef.get().getApplicationContext(),
                            "Adding course...", Toast.LENGTH_LONG).show();
                    SearchActivity.saveCourse(list.get(position));
                }
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
