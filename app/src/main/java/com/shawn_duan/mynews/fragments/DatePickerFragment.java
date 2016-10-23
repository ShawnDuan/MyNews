package com.shawn_duan.mynews.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by sduan on 10/22/16.
 */

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private int mYear, mMonth, mDay;

    public DatePickerFragment() {
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        onDateSetListener = listener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mYear = args.getInt("year");
        mMonth = args.getInt("month");
        mDay = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), onDateSetListener, mYear, mMonth, mDay);
    }
}