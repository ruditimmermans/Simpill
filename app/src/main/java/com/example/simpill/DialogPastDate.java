package com.example.simpill;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.res.ResourcesCompat;

public class DialogPastDate extends AppCompatDialogFragment {

    Simpill simpill;
    PillDBHelper myDatabase;
    Typeface truenoReg, truenoLight;
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;
    View dialogView;
    TextView titleTextView, titleMessageView;
    Button okBtn;
    Dialog pastDateDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        initAll();
        createDialogWithFormatting();
        createOnClickListeners();
        return pastDateDialog;
    }

    private void initAll() {
        initClasses();
        initFonts();
        initDialogBuilder();
        initView();
        initTextViewsAndButtons();
    }

    private void initTextViewsAndButtons() {
        titleTextView = dialogView.findViewById(R.id.dialogTitleTextView);
        titleMessageView = dialogView.findViewById(R.id.dialogMessageTextView);
        okBtn = dialogView.findViewById(R.id.btnOk);
    }

    private void initView() {
        loadSharedPrefs();
        inflater = getActivity().getLayoutInflater();
        setViewBasedOnTheme();
    }

    private void loadSharedPrefs() {
        SharedPreferences themePref = getContext().getSharedPreferences(Simpill.THEME_PREF_BOOLEAN, Context.MODE_PRIVATE);
        Boolean theme = themePref.getBoolean(Simpill.USER_THEME, true);
        simpill.setCustomTheme(theme);
        SharedPreferences is24HrPref= getContext().getSharedPreferences(Simpill.IS_24HR_BOOLEAN, Context.MODE_PRIVATE);
        Boolean is24Hr = is24HrPref.getBoolean(Simpill.USER_IS_24HR, true);
        simpill.setUserIs24Hr(is24Hr);
    }

    private void initClasses() {
        myDatabase = new PillDBHelper(getContext());
        simpill = new Simpill();
    }

    private void initDialogBuilder() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
    }

    private void initFonts() {
        truenoReg = ResourcesCompat.getFont(getContext(), R.font.truenoreg);
        truenoLight = ResourcesCompat.getFont(getContext(), R.font.truenolight);
    }

    private void setViewBasedOnTheme() {
        if (simpill.getCustomTheme())
        {
            dialogView = inflater.inflate(R.layout.past_date_dialog_layout, null);
        }
        else {
            dialogView = inflater.inflate(R.layout.past_date_dialog_layout_light, null);
        }
        dialogBuilder.setView(dialogView);
    }

    private void createOnClickListeners() {
        okBtn.setOnClickListener(view -> pastDateDialog.dismiss());
    }


    private void createDialogWithFormatting() {
        pastDateDialog = dialogBuilder.create();
        pastDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        titleTextView.setTypeface(truenoReg);
        titleMessageView.setTypeface(truenoReg);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleTextView.setLetterSpacing(0.025f);
            titleMessageView.setLetterSpacing(0.025f);
        }

        titleTextView.setTextSize(35.0f);
        titleMessageView.setTextSize(15.0f);
    }
}
