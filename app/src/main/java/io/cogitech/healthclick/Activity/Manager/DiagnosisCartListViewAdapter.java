package io.cogitech.healthclick.Activity.Manager;/*
package io.cogitech.diagnosis.Activity.Manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.cogitech.diagnosis.Activity.DiagnosisCart;
import io.cogitech.diagnosis.R;

public class DiagnosisCartListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<DiagnosisCart> diagnosisCarts = null;
    private ArrayList<DiagnosisCart> arraylist;

    public DiagnosisCartListViewAdapter(Context context, List<DiagnosisCart> diagnosisCarts) {
        mContext = context;
        this.diagnosisCarts = diagnosisCarts;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<DiagnosisCart>();
        this.arraylist.addAll(diagnosisCarts);
    }

    public class ViewHolder {
        public TextView description;
        public TextView name;
        public TextView love;
        public TextView unlove;
    }

    @Override
    public int getCount() {
        return diagnosisCarts.size();
    }

    @Override
    public DiagnosisCart getItem(int position) {
        return diagnosisCarts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.description = (TextView) view.findViewById(R.id.description);
            holder.love = view.findViewById(R.id.love);
            holder.unlove = view.findViewById(R.id.unlove);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(diagnosisCarts.get(position).getNom());
        holder.description.setText(diagnosisCarts.get(position).getAuteur());
        holder.unlove.setText(""+diagnosisCarts.get(position).getUnlove());
        holder.love.setText(""+diagnosisCarts.get(position).getLove());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        conseilList.clear();
        if (charText.length() == 0) {
            conseilList.addAll(arraylist);
        } else {
            for (Conseil wp : arraylist) {
                if (wp.getNom().toLowerCase(Locale.getDefault()).contains(charText)) {
                    conseilList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
*/
