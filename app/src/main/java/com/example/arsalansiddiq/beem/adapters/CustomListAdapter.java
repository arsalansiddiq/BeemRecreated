package com.example.arsalansiddiq.beem.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arsalansiddiq.beem.R;
import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesSKUArrayResponse;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<SalesSKUArrayResponse> {

    private Context context;
    List<SalesSKUArrayResponse> salesSKUArrayResponse;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<SalesSKUArrayResponse> objects) {
        super(context, 0, objects);

        this.context = context;
        this.salesSKUArrayResponse =  objects;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SalesSKUArrayResponse salesSKUArrayResponse = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_child, parent, false);
        }
//        return super.getView(position, convertView, parent);

        TextView txtView_name = convertView.findViewById(R.id.txtView_name);
        TextView txtView_brandImage = convertView.findViewById(R.id.txtView_brandImage);

        txtView_name.setText(salesSKUArrayResponse.getName());
        txtView_brandImage.setText(salesSKUArrayResponse.getSKUImage());

        return convertView;

    }




}
