package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.cogitech.healthclick.Model.Diagnosis;
import io.cogitech.healthclick.R;

public class DiagnosisListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Diagnosis> diagnosisList = new ArrayList<>();
    private ArrayList<Diagnosis> arraylist;
    private ArrayList<Integer> addedList = new ArrayList();

    public DiagnosisListViewAdapter(Context context, List<Diagnosis> diagnosisList) {
        mContext = context;
        this.diagnosisList = diagnosisList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Diagnosis>();
        this.arraylist.addAll(diagnosisList);
    }

    @Override
    public int getCount() {
        return diagnosisList.size();
    }

    @Override
    public Diagnosis getItem(int position) {
        return diagnosisList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items_cart, null);
            // Locate the TextViews in listview_item.xml
            holder.name = view.findViewById(R.id.name);
            holder.profname = view.findViewById(R.id.profname);
            holder.decription = view.findViewById(R.id.description);
            holder.accurency = view.findViewById(R.id.accuracy);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(diagnosisList.get(position).getIssue().getName());
        holder.decription.setText(diagnosisList.get(position).getIssue().getIcdName());
        holder.profname.setText("prof name " + diagnosisList.get(position).getIssue().getProfName());
        holder.accurency.setText((int) Double.parseDouble(diagnosisList.get(position).getIssue().getAccuracy()) + "%");

        if (Double.parseDouble(diagnosisList.get(position).getIssue().getAccuracy()) >= 75) {
            holder.accurency.setTextColor(Color.GREEN);
        } else if (Double.parseDouble(diagnosisList.get(position).getIssue().getAccuracy()) <= 50) {
            holder.accurency.setTextColor(Color.argb(100, 72, 150, 51));

        } else {
            holder.accurency.setTextColor(Color.argb(100, 138, 55, 98));
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        diagnosisList.clear();
        if (charText.length() == 0) {
            diagnosisList.addAll(arraylist);
        } else {
            for (Diagnosis wp : arraylist) {
                if (wp.getIssue().getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    diagnosisList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView name;
        public TextView profname;
        public TextView accurency;
        public TextView decription;

    }

}
