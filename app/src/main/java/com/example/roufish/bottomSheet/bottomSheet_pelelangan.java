package com.example.roufish.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roufish.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomSheet_pelelangan extends BottomSheetDialogFragment {

    public bottomSheet_pelelangan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_pelelangan, container, false);
    }
}
