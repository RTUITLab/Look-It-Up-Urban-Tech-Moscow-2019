package com.rtuitlab.lookitapp.urbantech.moscow.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.rtuitlab.lookitapp.urbantech.moscow.env.ItemClass;

import org.tensorflow.lite.examples.classification.R;

public class ItemClassAdapter extends ArrayAdapter<ItemClass> {

    public ItemClassAdapter(@NonNull Context context, ItemClass[] resource) {
        super(context, R.layout.item_class_shit, resource);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ItemClass item = getItem(position);


        if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_class_shit,null);}

        ((TextView) convertView.findViewById(R.id.tv)).setText(item.text);

        convertView.hasOnClickListeners();
        return convertView;
    }


}
