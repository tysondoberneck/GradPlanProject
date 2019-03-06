package com.example.gradplanproject;

import java.util.List;
import java.util.Map;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Map<String, String>> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseCode;
        public TextView courseName;
        public TextView courseDetails;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            courseCode = (TextView) v.findViewById(R.id.courseCode);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseDetails = (TextView) v.findViewById(R.id.courseDetails);
        }
    }


//    public void add(int position, String item) {
//        values.add(position, item);
//        notifyItemInserted(position);
//    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public RecyclerAdapter(List<Map<String, String>> dataset) {
        values = dataset;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.courseCode.setText(values.get(position).get("code"));
        holder.courseName.setText(values.get(position).get("name"));
        holder.courseName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.courseDetails.setText(values.get(position).get("details"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
