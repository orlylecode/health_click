package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.cogitech.healthclick.Model.Specialisation;
import io.cogitech.healthclick.R;

public class SpecialisationListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Specialisation> specialisations = new ArrayList<>();
    private ArrayList<Specialisation> arraylist;
    private ArrayList<Integer> addedList = new ArrayList();

    public SpecialisationListViewAdapter(Context context, List<Specialisation> specialisations) {
        mContext = context;
        this.specialisations = specialisations;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Specialisation>();
        this.arraylist.addAll(specialisations);
    }

    @Override
    public int getCount() {
        return specialisations.size();
    }

    @Override
    public Specialisation getItem(int position) {
        return specialisations.get(position);
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
            holder.name = view.findViewById(R.id.name);
            holder.Id = view.findViewById(R.id.Id);
            holder.chekbox = view.findViewById(R.id.check);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(specialisations.get(position).getName());
        holder.Id.setText("" + specialisations.get(position).getID());

        ///////////////////////random color ////////////////////////
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ///////////////////////random color ////////////////////////

        holder.Id.setBackgroundColor(color);
        holder.chekbox.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        specialisations.clear();
        if (charText.length() == 0) {
            specialisations.addAll(arraylist);
        } else {
            for (Specialisation wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    specialisations.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView name;
        public TextView Id;
        public ImageView chekbox;

    }

}
