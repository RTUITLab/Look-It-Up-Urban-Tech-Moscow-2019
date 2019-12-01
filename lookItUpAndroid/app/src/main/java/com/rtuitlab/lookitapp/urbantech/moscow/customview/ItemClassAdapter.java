package com.rtuitlab.lookitapp.urbantech.moscow.customview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtuitlab.lookitapp.urbantech.moscow.env.ItemClass;

import org.tensorflow.lite.examples.classification.R;

public class ItemClassAdapter extends ArrayAdapter<ItemClass> {

    public ItemClassAdapter(@NonNull Context context, int resource) {
        super(context, R.layout.item_class_shit, resource);
    }
}
