package com.example.roufish.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roufish.Beli_Langsung;
import com.example.roufish.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Bottom_Sheet_beliLangsung extends BottomSheetDialogFragment {

    public Bottom_Sheet_beliLangsung(Beli_Langsung beliLangsung){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_bottom_sheet_beli_langsung, container, false);
    }
}