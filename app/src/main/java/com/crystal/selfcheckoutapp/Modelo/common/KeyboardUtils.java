package com.crystal.selfcheckoutapp.Modelo.common;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class KeyboardUtils {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Fragment activity) {
        InputMethodManager imm = (InputMethodManager) activity.requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null && activity.requireActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.requireActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
