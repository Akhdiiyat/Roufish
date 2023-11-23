package com.example.roufish.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roufish.Beli_Langsung;
import com.example.roufish.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Bottom_Sheet_beliLangsung extends BottomSheetDialogFragment {

    public Bottom_Sheet_beliLangsung(Beli_Langsung beliLangsung){

    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        return getLayoutInflater().inflate(R.layout.activity_bottom_sheet_beli_langsung, container, false);
    }
}