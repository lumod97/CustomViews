package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.lumod97.customviews.R;
import com.github.lumod97.customviews.databinding.ValidatorInputLayoutBinding;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ValidatorInputLayout extends FrameLayout {

    private OnItemSelectListener listener;
    private boolean showDropdown = false;
    private ValidatorInputLayoutBinding binding;

    public ValidatorInputLayout(Context context) {
        super(context);
        init(context);
    }

    public ValidatorInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ValidatorInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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

    private void init(Context context) {
        // Crear un nuevo contexto con el tema de la librería
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, R.style.CustomViewsTheme);
        // Inflar el layout usando el nuevo contexto
        binding = ValidatorInputLayoutBinding.inflate(LayoutInflater.from(themeWrapper), this, true);
    }

    public void setHint(String hint) {
        binding.tilField.setHint(hint);
    }

    public void setEndIconMode(int endIconMode) {
        binding.tilField.setEndIconMode(endIconMode);
    }

    public void setClearIcon(int clearIcon) {
        binding.btnClear.setBackgroundResource(clearIcon);
    }

    public void setBtnClearImageResource(int endIconDrawable) {
        binding.btnClear.setImageResource(endIconDrawable);
    }

    public void setClearButtonBackground(int color) {
        binding.btnClear.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), color));
    }

    public void setTextInputIconDrawable(int endIconDrawable) {
        binding.tilField.setEndIconDrawable(endIconDrawable);
    }

    public void setTextInputHintEnabled(boolean hintEnabled) {
        binding.tilField.setHintEnabled(hintEnabled);
    }

    public void setTextInputHint(String hint) {
        binding.cboField.setHint(hint);
    }

    public void setAutocompleteAdapter(ValidatorInputLayoutAdapter adapter) {
        binding.cboField.setAdapter(adapter);
    }

    public void setAutocompleteItems(List<ValidatorInputLayoutItem> items) {
        ValidatorInputLayoutAdapter adapter = new ValidatorInputLayoutAdapter(getContext(), items);
        binding.cboField.setAdapter(adapter);
    }

    public void setShowDropdownOnFocus(boolean showDropdownOnFocus) {
        binding.cboField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.cboField.showDropDown();
            } else {
                binding.cboField.dismissDropDown();
            }
        });
    }

    public void toggleDropDown() {
        if (showDropdown) {
            binding.cboField.dismissDropDown();
            showDropdown = false;
        } else {
            binding.cboField.showDropDown();
            showDropdown = true;
        }
    }

    public void showDropDown() {
        binding.cboField.showDropDown();
    }

    public void dismissDropDown() {
        binding.cboField.dismissDropDown();
    }

    public void showKeyboardOnFocus(boolean showKeyboardOnFocus) {
        binding.cboField.setShowSoftInputOnFocus(false);
    }

    public void showKeyboardIcon(boolean showKeyboardIcon) {
        binding.tilField.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        binding.tilField.setEndIconVisible(showKeyboardIcon);
        binding.tilField.setEndIconDrawable(R.drawable.ic_keyboard);

        binding.tilField.setEndIconOnClickListener(v -> {
            showKeyboard();
            showDropDown();
        });
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void clearTextAndShowDropdown() {
        binding.cboField.setText("");
        binding.cboField.postDelayed(binding.cboField::showDropDown, 400);
    }

    private void setValidators(ValidationItem validationItem) {
        MaterialAutoCompleteTextView control = validationItem.getControl();
        String error = validationItem.getEmptyError();
        control.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                control.setTextColor(getContext().getResources().getColor(R.color.black));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout parent = getTextInputLayout(control);
                if (s.toString().isEmpty()) {
                    parent.setError(error);
                } else {
                    parent.setError(null);
                    parent.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                validationItem.getClearButton().setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

    }

    private void setClearButtonClickListener(ValidationItem validationItem) {
        validationItem.getClearButton().setOnClickListener(v -> {
            validationItem.getControl().setText("");
            validationItem.getClearButton().setVisibility(View.GONE);
            validationItem.getControl().requestFocus();
            validationItem.getControl().postDelayed(() -> {
                validationItem.getControl().showDropDown();
            }, 400);
        });
    }

    private void setEndIconClickListener(MaterialAutoCompleteTextView control) {
        TextInputLayout parent = getTextInputLayout(control);
        parent.setEndIconOnClickListener(v -> {  // 🔹 Cambia setStartIconOnClickListener por setEndIconOnClickListener
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            control.setText(""); // Borra el texto
            control.postDelayed(control::showDropDown, 400); // Muestra la lista desplegable
        });
    }

    public void setOnItemClickListener(OnItemSelectListener listener) {
        binding.cboField.setOnItemClickListener((parent, view, position, id) -> {
            ValidatorInputLayoutItem selectedItem = (ValidatorInputLayoutItem) parent.getItemAtPosition(position);
            listener.onItemSelected(selectedItem);
        });
    }

    public void setTreshhold(int treshhold) {
        binding.cboField.setThreshold(treshhold);
    }

    public interface OnItemSelectListener {
        void onItemSelected(ValidatorInputLayoutItem selectedItem);
    }
}
