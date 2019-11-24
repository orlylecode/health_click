package io.cogitech.healthclick.Activity.Manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import io.cogitech.healthclick.Model.Remede;
import io.cogitech.healthclick.R;

public class RemedeBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Remede> remedes;
    private ArrayList<Remede> originalList;

    public RemedeBaseExpandableListAdapter(Context context, ArrayList<Remede> remedes) {
        this.context = context;

        this.remedes = new ArrayList<Remede>();
        this.remedes.addAll(remedes);
        this.originalList = new ArrayList<Remede>();
        this.originalList.addAll(remedes);
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Remede remede = remedes.get(groupPosition);
        return remede;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Remede remede = (Remede) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_row, null);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView prix = convertView.findViewById(R.id.prix);

        name.setText(remede.getName());
        prix.setText(remede.getPrix());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Remede remede = (Remede) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row, null);
        }

        TextView posologie = convertView.findViewById(R.id.posologie);
        TextView laboratoire = convertView.findViewById(R.id.laboratoire);
        TextView principeActif = convertView.findViewById(R.id.principe_actif);
        TextView formeDosage = convertView.findViewById(R.id.forme_dosage);
        TextView classe = convertView.findViewById(R.id.classe);
        TextView conditionnement = convertView.findViewById(R.id.conditionnement);

        principeActif.setText(remede.getPrincipeActif());
        classe.setText(remede.getClasse());
        formeDosage.setText(remede.getFormeDosage());
        laboratoire.setText(remede.getLaboratoire());
        conditionnement.setText(remede.getConditionnement());
        posologie.setText(remede.getPosologie());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        remedes.clear();
        if (charText.length() == 0) {
            remedes.addAll(originalList);
        } else {
            for (Remede wp : originalList) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) | wp.getPrincipeActif().toLowerCase(Locale.getDefault()).contains(charText) | wp.getPrix().toLowerCase(Locale.getDefault()).contains(charText) | wp.getClasse().toLowerCase(Locale.getDefault()).contains(charText) | wp.getConditionnement().toLowerCase(Locale.getDefault()).contains(charText)) {
                    remedes.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
