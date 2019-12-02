package com.rtuitlab.lookitapp.urbantech.moscow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.classification.R;

import static android.provider.Telephony.Mms.Part.TEXT;

public class ItemSearchFragment extends Fragment {

    private String mText;

    ItemSearchFragment(){}

        public static ItemSearchFragment newInstance(String text){ // text это название класса товара
            ItemSearchFragment fragment  = new ItemSearchFragment();
            Bundle args = new Bundle();
            args.putString(TEXT, text);
            fragment.setArguments(args);
            return fragment;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            mText = getArguments().getString(TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_search_fragment, container,false);

        TextView tv = view.findViewById(R.id.tv);
        tv.setText(mText);
        tv.requestFocus();

        return view;
    }

}


