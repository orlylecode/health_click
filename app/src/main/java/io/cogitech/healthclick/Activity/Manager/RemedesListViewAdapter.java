package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.cogitech.healthclick.Model.Remede;
import io.cogitech.healthclick.R;

public class RemedesListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Remede> remedes = new ArrayList<>();
    private ArrayList<Remede> arraylist;
    private ArrayList<Integer> addedList = new ArrayList();

    public RemedesListViewAdapter(Context context, List<Remede> remedes) {
        mContext = context;
        this.remedes = remedes;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Remede>();
        this.arraylist.addAll(remedes);
    }

    @Override
    public int getCount() {
        return remedes.size();
    }

    @Override
    public Remede getItem(int position) {
        return remedes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items_remede, null);
            // Locate the TextViews in listview_item.xml
            holder.name = view.findViewById(R.id.name);
            holder.principeActif = view.findViewById(R.id.principe_actif);
            holder.conditionnement = view.findViewById(R.id.conditionnement);
            holder.classe = view.findViewById(R.id.classe);
            holder.laboratoire = view.findViewById(R.id.laboratoire);
            holder.formeDosage = view.findViewById(R.id.forme_dosage);
            holder.prix = view.findViewById(R.id.prix);
            holder.posologie = view.findViewById(R.id.posologie);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(HtmlCompat.fromHtml(remedes.get(position).getName(), Html.FROM_HTML_MODE_COMPACT));
        holder.principeActif.setText(HtmlCompat.fromHtml(remedes.get(position).getPrincipeActif(), Html.FROM_HTML_MODE_COMPACT));
        holder.classe.setText(HtmlCompat.fromHtml(remedes.get(position).getClasse(), Html.FROM_HTML_MODE_COMPACT));
        holder.formeDosage.setText(HtmlCompat.fromHtml(remedes.get(position).getFormeDosage(), Html.FROM_HTML_MODE_COMPACT));
        holder.laboratoire.setText(HtmlCompat.fromHtml(remedes.get(position).getLaboratoire(), Html.FROM_HTML_MODE_COMPACT));
        holder.conditionnement.setText(HtmlCompat.fromHtml(remedes.get(position).getConditionnement(), Html.FROM_HTML_MODE_COMPACT));
        holder.prix.setText(HtmlCompat.fromHtml(remedes.get(position).getPrix(), Html.FROM_HTML_MODE_COMPACT));
        holder.posologie.setText(HtmlCompat.fromHtml(remedes.get(position).getPosologie(), Html.FROM_HTML_MODE_COMPACT));

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        String s = charText;
        charText = charText.toLowerCase(Locale.getDefault());
        remedes.clear();
        if (charText.length() == 0) {
            remedes.addAll(arraylist);
        } else {
            for (Remede wp : arraylist) {
                wp.setName(wp.getName().replace("<font color='red'>", ""));
                wp.setName(wp.getName().replace("</font>", ""));

                wp.setPrincipeActif(wp.getPrincipeActif().replace("<font color='red'>", ""));
                wp.setPrincipeActif(wp.getPrincipeActif().replace("</font>", ""));

                wp.setPrix(wp.getPrix().replace("<font color='red'>", ""));
                wp.setPrix(wp.getPrix().replace("</font>", ""));

                wp.setClasse(wp.getClasse().replace("<font color='red'>", ""));
                wp.setClasse(wp.getClasse().replace("</font>", ""));

                wp.setConditionnement(wp.getConditionnement().replace("<font color='red'>", ""));
                wp.setConditionnement(wp.getConditionnement().replace("</font>", ""));

                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) | wp.getPrincipeActif().toLowerCase(Locale.getDefault()).contains(charText) | wp.getPrix().toLowerCase(Locale.getDefault()).contains(charText) | wp.getClasse().toLowerCase(Locale.getDefault()).contains(charText) | wp.getConditionnement().toLowerCase(Locale.getDefault()).contains(charText)) {
                    wp.setName(wp.getName().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    wp.setPrincipeActif(wp.getPrincipeActif().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    wp.setPrix(wp.getPrix().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    wp.setClasse(wp.getClasse().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    wp.setConditionnement(wp.getConditionnement().toLowerCase().replace(charText, "<font color='red'>" + charText + "</font>"));
                    remedes.add(wp);

                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView name;
        public TextView prix;
        public TextView classe;
        public TextView conditionnement;
        public TextView laboratoire;
        public TextView formeDosage;
        public TextView principeActif;
        public TextView posologie;

    }

}
