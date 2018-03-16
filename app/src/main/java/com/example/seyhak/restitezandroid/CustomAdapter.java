package com.example.seyhak.restitezandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seyhak on 13.03.2018.
 */
//TA KLASA UMOŻLIWA ZAMIANĘ LISTY SKŁADNIKU MENU W LISTVIEW
public class CustomAdapter extends ArrayAdapter<SkładnikMenu> implements View.OnClickListener{

    private List<SkładnikMenu> dataSet;
    Context mContext;// view lookup cache stored in tag
    private ViewHolder viewHolder;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtPrice;
    }

    public CustomAdapter(List<SkładnikMenu> data, Context context) {
        super(context, R.layout.element_menu, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        //SkładnikMenu dataModel=(SkładnikMenu)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SkładnikMenu dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.element_menu, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.textView2_nazwa);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.textView3_rodzaj);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.textView4_cena);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

       // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getNazwaSM());
        viewHolder.txtType.setText(dataModel.getRodzajSM());
        viewHolder.txtPrice.setText(Double.toString(dataModel.getCenaSM()));
        viewHolder.txtPrice.setOnClickListener(this);
        viewHolder.txtPrice.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}