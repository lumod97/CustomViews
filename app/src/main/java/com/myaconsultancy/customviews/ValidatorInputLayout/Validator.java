package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.github.lumod97.customviews.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validator {
    @RequiresApi(api = Build.VERSION_CODES.N)
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Validator(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean validateForm(List<ValidatorInputLayout> validationItems) {
        List<String> errors = new ArrayList<>();
        for (ValidatorInputLayout validationItem :
                validationItems) {
            MaterialAutoCompleteTextView materialAutoCompleteTextView = validationItem.getAutoCompleteTextView();
            String emptyError = validationItem.getEmptyText();
            TextInputLayout textInputLayout = validationItem.getTextInputLayout();

            if (materialAutoCompleteTextView.getText().toString().isEmpty()) {
                errors.add(emptyError);
                assert textInputLayout != null;
                textInputLayout.setError(emptyError);
                textInputLayout.setErrorEnabled(true);
            } else {
                assert textInputLayout != null;
                textInputLayout.setErrorEnabled(false);
            }
        }
        return errors.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean validateForm(HashMap<ValidatorInputLayout, List<ValidatorInputLayoutItem>> validationItems) {
        List<String> errors = new ArrayList<>();

        validationItems.forEach((validationItem, validatorInputLayoutItem) -> {
            MaterialAutoCompleteTextView materialAutoCompleteTextView = validationItem.getAutoCompleteTextView();
            String emptyError = validationItem.getEmptyText();
            String invalidError = validationItem.getInvalidText();
            TextInputLayout textInputLayout = validationItem.getTextInputLayout();

            boolean itemExists = validatorInputLayoutItem.stream().
                    anyMatch(item -> item.getDescripcion().equals(materialAutoCompleteTextView.getText().toString()));

            if (!itemExists && !materialAutoCompleteTextView.getText().toString().isEmpty()) {
                errors.add(invalidError);
                assert textInputLayout != null;
                textInputLayout.setError(invalidError);
                textInputLayout.setErrorEnabled(true);
                materialAutoCompleteTextView.setTextColor(context.getResources().getColor(R.color.alert));
            } else if (materialAutoCompleteTextView.getText().toString().isEmpty()) {
                errors.add(emptyError);
                assert textInputLayout != null;
                textInputLayout.setError(emptyError);
                textInputLayout.setErrorEnabled(true);
            } else {
                assert textInputLayout != null;
                textInputLayout.setErrorEnabled(false);
            }
        });
        return errors.isEmpty();
    }
}
