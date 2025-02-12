package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.github.lumod97.customviews.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    @RequiresApi(api = Build.VERSION_CODES.N)
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Validator(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean validateForm(List<ValidationItem> validationItems) {
        AttributeSet attrs = null;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ValidatorInputLayout);
        List<String> errors = new ArrayList<>();
        for (ValidationItem validationItem :
                validationItems) {
            MaterialAutoCompleteTextView control = validationItem.getControl();
            String emptyError = validationItem.getEmptyError();
            String invalidError = validationItem.getInvalidError();
            TextInputLayout parent = getTextInputLayout(control);

            List<ValidatorInputLayoutItem> daoItems = validationItem.getValidatorInputLayoutItem();

            boolean itemExists = daoItems.stream()
                    .anyMatch(daoItem -> daoItem.getDescripcion().equals(control.getText().toString()));

            if (!itemExists && !control.getText().toString().isEmpty()) {
                errors.add(invalidError);
                assert parent != null;
                parent.setError(invalidError);
                parent.setErrorEnabled(true);
                control.setTextColor(context.getResources().getColor(R.color.alert));
            } else if (control.getText().toString().isEmpty()) {
                errors.add(emptyError);
                assert parent != null;
                parent.setError(emptyError);
                parent.setErrorEnabled(true);
            } else {
                assert parent != null;
                parent.setErrorEnabled(false);
            }
        }
        return errors.isEmpty();
    }

    private static TextInputLayout getTextInputLayout(EditText editText) {
        View parent = (View) editText.getParent();
        while (parent != null) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
            parent = (View) parent.getParent();
        }
        return null;
    }
}
