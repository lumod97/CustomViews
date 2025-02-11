package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.widget.ImageButton;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class ValidationItem {
    MaterialAutoCompleteTextView control;
    String emptyError;
    String invalidError;
    List<ValidatorInputLayoutItem> validatorInputLayoutItems;
    ImageButton clearButton;

    public ImageButton getClearButton() {
        return clearButton;
    }

    public void setClearButton(ImageButton clearButton) {
        this.clearButton = clearButton;
    }

    public MaterialAutoCompleteTextView getControl() {
        return control;
    }

    public void setControl(MaterialAutoCompleteTextView control) {
        this.control = control;
    }

    public String getEmptyError() {
        return emptyError;
    }

    public void setEmptyError(String emptyError) {
        this.emptyError = emptyError;
    }

    public String getInvalidError() {
        return invalidError;
    }

    public void setInvalidError(String invalidError) {
        this.invalidError = invalidError;
    }

    public List<ValidatorInputLayoutItem> getValidatorInputLayoutItem() {
        return validatorInputLayoutItems;
    }

    public void setValidatorInputLayoutItem(List<ValidatorInputLayoutItem> validatorInputLayoutItems) {
        this.validatorInputLayoutItems = validatorInputLayoutItems;
    }

    public void clearItem() {
        this.control = null;
        this.emptyError = null;
        this.invalidError = null;
        if (this.validatorInputLayoutItems != null) {
            this.validatorInputLayoutItems.clear();
        }
    }
}