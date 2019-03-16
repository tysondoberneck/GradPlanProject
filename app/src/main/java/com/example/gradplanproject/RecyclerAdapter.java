package com.example.gradplanproject;

import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // The RecyclerView.Adapter class must be extended and overloaded by anyone wishing to use
    // the RecyclerView. The ViewHolder is a class that must also be overloaded inside this adapter;
    // this class can contain multiple Views (textViews, imageViews, all sorts of stuff) and
    // represents a single item or object in the list represented by the RecyclerView.

    private List<Map<String, String>> values;

    public class ViewHolder extends RecyclerView.ViewHolder {

        //public TextView courseCode;
        public TextView courseName;
        public TextView courseDetails;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            //courseCode = (TextView) v.findViewById(R.id.courseCode);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseDetails = (TextView) v.findViewById(R.id.courseDetails);
        }
    }

    // Provided by the tutorial; neither are useful yet, but could be modified for use later...

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

    public RecyclerAdapter(List<Map<String, String>> courseList) {
        values = courseList;
    }

    // The following overridden methods are called by the LayoutManager as necessary. We should
    // never have to call them ourselves.

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflater uses layout XML file, which we must also provide, to set the layout for each
        // viewHolder.

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // The LayoutManager loops through the list provided by the Adapter, providing a new
        // position index each time.

        String firstLine = values.get(position).get("code") + " - " + values.get(position).get("name");
        String secondLine = values.get(position).get("details");

        //holder.courseCode.setText(values.get(position).get("code"));
        holder.courseName.setText(firstLine);
        holder.courseDetails.setText(secondLine);

        // Provided onClickListener; currently set to remove an item when clicked on, but could
        // be made more useful later.

        /*
        holder.courseName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        */
    }

    // Another method called by LayoutManager to know how many ViewHolders to set.

    @Override
    public int getItemCount() {
        return values.size();
    }
}
