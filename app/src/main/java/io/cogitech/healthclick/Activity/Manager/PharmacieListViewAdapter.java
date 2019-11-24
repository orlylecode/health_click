package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.cogitech.healthclick.Model.Pharmacie;
import io.cogitech.healthclick.R;

public class PharmacieListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Pharmacie> pharmaciesList = new ArrayList<>();
    private ArrayList<Pharmacie> arraylist;
    private ArrayList<Integer> addedList = new ArrayList();

    public PharmacieListViewAdapter(Context context, List<Pharmacie> pharmaciesList) {
        mContext = context;
        this.pharmaciesList = pharmaciesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Pharmacie>();
        this.arraylist.addAll(pharmaciesList);
    }

    @Override
    public int getCount() {
        return pharmaciesList.size();
    }

    @Override
    public Pharmacie getItem(int position) {
        return pharmaciesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items_pharmacie, null);
            // Locate the TextViews in listview_item.xml
            holder.name = view.findViewById(R.id.name);
            holder.date = view.findViewById(R.id.date);
            holder.localisation = view.findViewById(R.id.localisation);
            holder.ville = view.findViewById(R.id.ville);
            holder.contact = view.findViewById(R.id.contact);
            holder.region = view.findViewById(R.id.region);
            holder.groupe = view.findViewById(R.id.groupe);
            holder.layout = view.findViewById(R.id.layout);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(HtmlCompat.fromHtml(pharmaciesList.get(position).getB(), Html.FROM_HTML_MODE_COMPACT));
        holder.date.setText(pharmaciesList.get(position).getDateGarde());
        holder.ville.setText(pharmaciesList.get(position).getC());
        holder.localisation.setText(pharmaciesList.get(position).getD());
        holder.groupe.setText(pharmaciesList.get(position).getGroupeGarde());
        holder.region.setText(pharmaciesList.get(position).getA());
        holder.contact.setText(pharmaciesList.get(position).getE());

        if (pharmaciesList.get(position).getDateGarde() != null) {
            holder.layout.setBackgroundColor(Color.argb(100, 72, 150, 51));
        } else {
            holder.groupe.setText("Aucun");
            holder.date.setText("Inconnue");
            holder.layout.setBackgroundColor(Color.argb(100, 138, 55, 98));
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        pharmaciesList.clear();
        if (charText.length() == 0) {
            pharmaciesList.addAll(arraylist);
        } else {
            for (Pharmacie wp : arraylist) {
                wp.setB(wp.getB().replace("<font color='red'>", ""));
                wp.setB(wp.getB().replace("</font>", ""));

                if (wp.getB().toLowerCase(Locale.getDefault()).contains(charText) | wp.getC().toLowerCase(Locale.getDefault()).contains(charText) | wp.getA().toLowerCase(Locale.getDefault()).contains(charText)) {
                    wp.setB(wp.getB().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    pharmaciesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView name;
        public TextView localisation;
        public TextView ville;
        public TextView region;
        public TextView contact;
        public TextView date;
        public TextView groupe;
        public RelativeLayout layout;

    }

}
