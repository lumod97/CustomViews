package com.myaconsultancy.customviews.ValidatorInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    private String emptyText = null;
    private String invalidText = null;

    public ValidatorInputLayout(Context context) {
        super(context);
        init(context);
    }

    public ValidatorInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
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

    private void init(Context context, AttributeSet attrs) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, R.style.CustomViewsTheme);
        binding = ValidatorInputLayoutBinding.inflate(LayoutInflater.from(themeWrapper), this, true);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ValidatorInputLayout);

            try {
                // Obtener valores desde attrs.xml
                String hintText = a.getString(R.styleable.ValidatorInputLayout_textInputHint);
                int threshold = a.getInt(R.styleable.ValidatorInputLayout_threshold, 3);
                int btnClearImageResource = a.getResourceId(R.styleable.ValidatorInputLayout_btnClearImageResource, R.drawable.error_center_x);
                int clearButtonBackground = a.getResourceId(R.styleable.ValidatorInputLayout_clearButtonBackground, R.color.float_transparent);
                int rightIconMode = a.getInt(R.styleable.ValidatorInputLayout_rightIconMode, TextInputLayout.END_ICON_NONE);
                int textInputIconDrawable = a.getResourceId(R.styleable.ValidatorInputLayout_textInputIconDrawable, R.drawable.ic_keyboard);
                boolean textInputHintEnabled = a.getBoolean(R.styleable.ValidatorInputLayout_textInputHintEnabled, false);
                boolean showDropdownOnFocus = a.getBoolean(R.styleable.ValidatorInputLayout_showDropdownOnFocus, false);
                boolean showKeyboardOnFocus = a.getBoolean(R.styleable.ValidatorInputLayout_showKeyboardOnFocus, false);
                boolean showKeyboardIcon = a.getBoolean(R.styleable.ValidatorInputLayout_showKeyboardIcon, false);
                boolean showClearButton = a.getBoolean(R.styleable.ValidatorInputLayout_showClearButton, false);
                boolean clearTextAndShowDropdown = a.getBoolean(R.styleable.ValidatorInputLayout_clearTextAndShowDropdown, false);
                int textInputType = a.getInt(R.styleable.ValidatorInputLayout_textInputType, 0);
                boolean autocompleteTextView = a.getBoolean(R.styleable.ValidatorInputLayout_autocompleteTextView, false);

                String emptyText = a.getString(R.styleable.ValidatorInputLayout_emptyText);
                String invalidText = a.getString(R.styleable.ValidatorInputLayout_invalidText);

                this.emptyText = emptyText;
                this.invalidText = invalidText;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setValidators(emptyText, invalidText);
                }

//                binding.cboField.setVisibility(autocompleteTextView ? View.VISIBLE : View.GONE);
//                binding.edtField.setVisibility(autocompleteTextView ? View.GONE : View.VISIBLE);


                if (binding.cboField != null) {
                    if (hintText != null) {
                        setTextInputHint(hintText);
                    }
                }

                setTreshhold(threshold);
                setShowClearButton(showClearButton);
                setBtnClearImageResource(btnClearImageResource);
                setClearButtonBackground(clearButtonBackground);
                setEndIconMode(rightIconMode, textInputIconDrawable);
                setTextInputHintEnabled(textInputHintEnabled);
                setShowDropdownOnFocus(showDropdownOnFocus);
                showKeyboardOnFocus(showKeyboardOnFocus);
                showKeyboardIcon(showKeyboardIcon);
                clearTextAndShowDropdown(clearTextAndShowDropdown);
                switch (textInputType) {
                    case 1:
                        binding.cboField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    case 2:
                        binding.cboField.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case 3:
                        binding.cboField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case 4:
                        binding.cboField.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    default:
                        binding.cboField.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
            } finally {
                a.recycle();
            }
        }
    }

    private void init(Context context) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, R.style.CustomViewsTheme);
        binding = ValidatorInputLayoutBinding.inflate(LayoutInflater.from(themeWrapper), this, true);
    }

    public void setShowClearButton(boolean showClearButton) {
        binding.btnClear.setVisibility(showClearButton ? View.VISIBLE : View.GONE);
    }

    public void setEndIconMode(int rightIconMode, int textInputIconDrawable) {
        switch (rightIconMode) {
            case 1:
                binding.tilField.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                break;
            case 2:
                binding.tilField.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                break;
            case 3:
                binding.tilField.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
                setTextInputIconDrawable(textInputIconDrawable);
                break;
            default:
                binding.tilField.setEndIconMode(TextInputLayout.END_ICON_NONE);
                break;
        }
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
        binding.tilField.setHint(hint);
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
                if (showDropdownOnFocus) {
                    binding.cboField.showDropDown();
                } else {
                    binding.cboField.dismissDropDown();
                }
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
        binding.cboField.setShowSoftInputOnFocus(showKeyboardOnFocus);
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

    private void clearTextAndShowDropdown(boolean clearTextAndShowDropdown) {
        if (clearTextAndShowDropdown) {
            binding.cboField.setText("");
            binding.cboField.postDelayed(binding.cboField::showDropDown, 400);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setValidators(String emptyText, String invalidText) {
        binding.cboField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.cboField.setTextColor(getContext().getResources().getColor(R.color.black));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.tilField.setError(emptyText == null ? emptyText : "Campo requerido");
                } else {
                    binding.tilField.setError(null);
                    binding.tilField.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnClear.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
            }
        });
    }

    public void setClearButtonClickListener(OnIconsClickListener listener) {
        binding.btnClear.setOnClickListener(v -> {
            binding.cboField.setText("");
            binding.btnClear.setVisibility(View.GONE);
            binding.cboField.requestFocus();
            binding.cboField.postDelayed(() -> {
                binding.cboField.showDropDown();
            }, 400);

            if (listener != null) {
                listener.onClearButtonClick();
            }
        });
    }

    public String getEmptyText() {
        return emptyText;
    }

    public String getInvalidText() {
        return invalidText;
    }

    public void setEndIconClickListener(OnIconsClickListener listener) {
        binding.tilField.setEndIconOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            binding.cboField.setText("");
            binding.cboField.postDelayed(binding.cboField::showDropDown, 400);

            if (listener != null) {
                listener.onEndIconClick();
            }
        });
    }

    public void setOnItemClickListener(OnItemSelectListener listener) {
        binding.cboField.setOnItemClickListener((parent, view, position, id) -> {

            Object selectedItem = parent.getItemAtPosition(position);

            if (selectedItem instanceof ValidatorInputLayoutItem) {
                listener.onItemSelected((ValidatorInputLayoutItem) selectedItem);
            } else if (selectedItem instanceof String) {
                listener.onTextSelected((String) selectedItem);
            }

        });
    }

    public ImageButton getClearButton() {
        return binding.btnClear;
    }

    public TextInputLayout getTextInputLayout() {
        return binding.tilField;
    }

    public MaterialAutoCompleteTextView getAutoCompleteTextView() {
        return binding.cboField;
    }

//    public TextInputEditText getEditText() {
//        return binding.edtField;
//    }

    public void setTreshhold(int treshhold) {
        binding.cboField.setThreshold(treshhold);
    }

    public interface OnItemSelectListener {
        void onItemSelected(ValidatorInputLayoutItem selectedItem);

        void onTextSelected(String selectedText);
    }

    public interface OnIconsClickListener {
        void onClearButtonClick();

        void onEndIconClick();
    }
}
