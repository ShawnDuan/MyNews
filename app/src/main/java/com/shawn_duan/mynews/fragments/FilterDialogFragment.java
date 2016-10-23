package com.shawn_duan.mynews.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.activities.MainActivity;
import com.shawn_duan.mynews.models.FilterSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by sduan on 10/22/16.
 */

public class FilterDialogFragment extends DialogFragment {
    private final static String TAG = FilterDialogFragment.class.getSimpleName();

    @BindView(R.id.tvBeginDate)
    TextView tvBeginDate;
    @BindView(R.id.spSort)
    Spinner spSort;
    @BindView(R.id.cbArt)
    CheckBox cbArt;
    @BindView(R.id.cbFashion)
    CheckBox cbFashioin;
    @BindView(R.id.cbSports)
    CheckBox cbSports;
    @BindView(R.id.btSave)
    Button btSave;

    private Unbinder unbinder;
    private FilterSettings mFilterSettings;

    public interface FilterDialogListener {
        void onFinishFilterDialog(FilterSettings filterSettings);
    }

    public static FilterDialogFragment newInstance(FilterSettings filterSettings) {
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("filter-settings", filterSettings);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
        mFilterSettings = getArguments().getParcelable("filter-settings");
        if (mFilterSettings == null) {
            mFilterSettings = new FilterSettings();
        } else {
            initDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout((6 * getResources().getDisplayMetrics().widthPixels)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvBeginDate)
    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        String beginDate = null;
        if (mFilterSettings != null) {
            beginDate = mFilterSettings.getBeginDate();
        }
        if (beginDate != null && beginDate.length() > 0) {
            try {
                Date date = new SimpleDateFormat("yyyyMMdd").parse(beginDate);
                calendar.setTime(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(args);
        datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d(TAG, "y: " + year + ",m: " + monthOfYear + ",d: " + dayOfMonth);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                Date date = calendar.getTime();
                String dateString = getDateString(date);
                mFilterSettings.setBeginDate(dateString);
                setDateText(date);
            }
        });
        datePickerFragment.show(getFragmentManager(), DatePickerFragment.class.getSimpleName());
    }

    @OnClick(R.id.btSave)
    public void saveFilter() {
        FilterDialogListener listener = ((MainActivity)getActivity()).mResultsFragment; // todo need to de-couple this. (resultsFragment <-> FilterDialogFragment)
        if (listener != null) {
            mFilterSettings.setSortOrder(spSort.getSelectedItem().toString());
            List<String> newsDeskArrayList = new ArrayList<>();
            if (cbArt.isChecked()) {
                newsDeskArrayList.add(cbArt.getText().toString());
            }
            if (cbSports.isChecked()) {
                newsDeskArrayList.add(cbSports.getText().toString());
            }
            if (cbFashioin.isChecked()) {
                newsDeskArrayList.add(cbFashioin.getText().toString());
            }
            mFilterSettings.setNewsDeskValues(newsDeskArrayList);
            listener.onFinishFilterDialog(mFilterSettings);
        }

        dismiss();
    }

    // private methods

    private void initDialog() {
        setDateText(mFilterSettings.getBeginDate());
        setSpinnerToValue(mFilterSettings.getSortOrder());
        List<String> newsDeskValues = mFilterSettings.getNewsDeskValues();
        if (newsDeskValues != null) {
            if (newsDeskValues.contains(cbArt.getText().toString())) {
                cbArt.setChecked(true);
            }

            if (newsDeskValues.contains(cbSports.getText().toString())) {
                cbSports.setChecked(true);
            }

            if (newsDeskValues.contains(cbFashioin.getText().toString())) {
                cbFashioin.setChecked(true);
            }
        }
    }

    private void setSpinnerToValue(String value) {
        int index = 0;
        SpinnerAdapter adapter = spSort.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break;
            }
        }
        spSort.setSelection(index);
    }


    private String getDateString(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    private void setDateText(Date date) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String newDateString = newDateFormat.format(date);
        tvBeginDate.setText(newDateString);
    }

    private void setDateText(String dateString) {
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = oldDateFormat.parse(dateString);

            SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String newDateString = newDateFormat.format(date);
            tvBeginDate.setText(newDateString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
