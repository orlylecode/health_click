package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.cogitech.healthclick.Model.Symptom;
import io.cogitech.healthclick.R;

public class SymptomListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Symptom> symptomeList = new ArrayList<>();
    private ArrayList<Symptom> arraylist;
    private ArrayList<Integer> addedList = new ArrayList();

    public SymptomListViewAdapter(Context context, List<Symptom> symptomeList) {
        mContext = context;
        this.symptomeList = symptomeList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Symptom>();
        this.arraylist.addAll(symptomeList);
    }

    @Override
    public int getCount() {
        return symptomeList.size();
    }

    @Override
    public Symptom getItem(int position) {
        return symptomeList.get(position);
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

        holder.name.setText(HtmlCompat.fromHtml(symptomeList.get(position).getName(), Html.FROM_HTML_MODE_COMPACT));
        holder.Id.setText("" + symptomeList.get(position).getID());

       /* ///////////////////////random color ////////////////////////
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ///////////////////////random color ////////////////////////

        holder.Id.setBackgroundColor(color);*/

        if (addedList.contains(symptomeList.get(position).getID())) {
            holder.chekbox.setImageResource(R.drawable.ic_check_box_black_24dp);
        } else {
            holder.chekbox.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        symptomeList.clear();
        if (charText.length() == 0) {
            symptomeList.addAll(arraylist);
        } else {
            for (Symptom wp : arraylist) {
                wp.setName(wp.getName().replace("<font color='red'>", ""));
                wp.setName(wp.getName().replace("</font>", ""));
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    wp.setName(wp.getName().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    symptomeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setAddedList(Integer position, String text) {
        if (this.addedList.contains(position)) {
            this.addedList.remove(position);
        } else {
            this.addedList.add(position);
        }

        filter(text);
    }

    public ArrayList<Integer> getAddedList() {
        return addedList;
    }

    public class ViewHolder {
        public TextView name;
        public TextView Id;
        public ImageView chekbox;

    }
}

