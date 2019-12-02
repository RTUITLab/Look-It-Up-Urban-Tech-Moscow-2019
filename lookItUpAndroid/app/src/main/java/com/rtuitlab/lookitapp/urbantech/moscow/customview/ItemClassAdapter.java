package com.rtuitlab.lookitapp.urbantech.moscow.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.rtuitlab.lookitapp.urbantech.moscow.ItemSearchFragment;
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

        convertView.findViewById(R.id.bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemSearchFragment fragment = ItemSearchFragment.newInstance(item.text);
                FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();//открыть item
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_to_right, R.anim.exit_to_right,R.anim.enter_to_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.replace(R.id.container, fragment,"Item_Choosing").commit();
            }
        });
        return convertView;
    }


}
