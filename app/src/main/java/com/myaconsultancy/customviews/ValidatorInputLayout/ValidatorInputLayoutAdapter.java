package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ValidatorInputLayoutAdapter extends ArrayAdapter<ValidatorInputLayoutItem> {
    public ValidatorInputLayoutAdapter(@NonNull Context context, List<ValidatorInputLayoutItem> daoItems) {
        super(context, android.R.layout.simple_spinner_dropdown_item, daoItems);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
