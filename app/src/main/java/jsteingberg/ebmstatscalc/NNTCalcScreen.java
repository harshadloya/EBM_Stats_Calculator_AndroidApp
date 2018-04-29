package jsteingberg.ebmstatscalc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import jsteingberg.ebmstatscalc.util.HelperView;

public class NNTCalcScreen extends Fragment
{
    private Button moreInfoBtn;
    private ConstraintLayout nntParent;
    private EditText rateEditText1;
    private EditText rateEditText2;
    private TextView rateTextView3;
    private EditText rateEditText4;
    private EditText rateEditText5;
    private TextView rateTextView6;
    private EditText rateEditText7;
    private EditText rateEditText8;
    private EditText rateEditText9;
    private EditText rateEditText10;
    private TextView rateTextView11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.nntcalcscreen, container, false);

        initializeComponents(view);
        setFilters();
        assignListeners(view);

        NNTMoreInfoScreen nntMoreInfoScreen = new NNTMoreInfoScreen();
        HelperView helperView = new HelperView(nntMoreInfoScreen, getFragmentManager(), "replaceWithNNTMoreInfoScreen");
        moreInfoBtn.setOnClickListener(helperView.moreInfoBtnListener);

        return view;
    }

    private void initializeComponents(View view)
    {
        moreInfoBtn = (Button) view.findViewById(R.id.nnt_moreInfoBtn);

        nntParent = (ConstraintLayout) view.findViewById(R.id.nnt_parent);

        rateEditText1 = (EditText) view.findViewById(R.id.nnt_rateEdit1);
        rateEditText2 = (EditText) view.findViewById(R.id.nnt_rateEdit2);
        rateTextView3 = (TextView) view.findViewById(R.id.nnt_rateEdit3);
        rateEditText4 = (EditText) view.findViewById(R.id.nnt_rateEdit4);
        rateEditText5 = (EditText) view.findViewById(R.id.nnt_rateEdit5);
        rateTextView6 = (TextView) view.findViewById(R.id.nnt_rateEdit6);
        rateEditText7 = (EditText) view.findViewById(R.id.nnt_rateEdit7);
        rateEditText8 = (EditText) view.findViewById(R.id.nnt_rateEdit8);
        rateEditText9 = (EditText) view.findViewById(R.id.nnt_rateEdit9);
        rateEditText10 = (EditText) view.findViewById(R.id.nnt_rateEdit10);
        rateTextView11 = (TextView) view.findViewById(R.id.nnt_rateEdit11);
    }

    private void setFilters()
    {
        HelperView.setFiltersEditText_2Decimals(rateEditText1, "0.00", "1.00");
        HelperView.setFiltersEditText_2Decimals(rateEditText2, "0.00", "1.00");
        HelperView.setFiltersEditText(rateEditText4, "0", "100");
        HelperView.setFiltersEditText(rateEditText5, "0", "100");
        HelperView.setFiltersEditText(rateEditText7, "0", "100");
        HelperView.setFiltersEditText(rateEditText8, "1", "100");
        HelperView.setFiltersEditText(rateEditText9, "0", "100");
        HelperView.setFiltersEditText(rateEditText10, "1", "100");
    }

    private void assignListeners(View v)
    {
        //rateEditText1.setOnFocusChangeListener(watcherFocusChangeListener);
        //for(EditText rateEditTexts: v)
        editTextsRecurseListeners((ViewGroup) v);
    }

    private void editTextsRecurseListeners(ViewGroup container) {
        int count = container.getChildCount();

        for (int i = 0; i < count; i++) {
            View child = container.getChildAt(i);
            if (child instanceof EditText)
            {
                //rateEditText = (EditText) child;
                //int currId = rateEditText.getId();
                //((EditText) child).setOnFocusChangeListener(watcherFocusChangeListener);
                child.setOnFocusChangeListener(watcherFocusChangeListener);
                ((EditText) child).setOnEditorActionListener(editTextsEditorActionListener);
            }
            else if (child instanceof ViewGroup) {
                //recurse through children views
                editTextsRecurseListeners((ViewGroup) child);
            }
        }
    }

    private View.OnFocusChangeListener watcherFocusChangeListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            switch(v.getId())
            {
                case R.id.nnt_rateEdit1:
                case R.id.nnt_rateEdit2:
                    if(hasFocus) {
                        ((EditText) v).addTextChangedListener(set1Watcher);
                    }
                    else /*if(!hasFocus)*/ {
                        ((EditText) v).removeTextChangedListener(set1Watcher);
                    }
                    break;
                case R.id.nnt_rateEdit4:
                case R.id.nnt_rateEdit5:
                    if(hasFocus) {
                        ((EditText) v).addTextChangedListener(set2Watcher);
                    }
                    else /*if(!hasFocus)*/ {
                        ((EditText) v).removeTextChangedListener(set2Watcher);
                    }
                    break;

                case R.id.nnt_rateEdit7:
                case R.id.nnt_rateEdit8:
                case R.id.nnt_rateEdit9:
                case R.id.nnt_rateEdit10:
                    if(hasFocus) {
                        ((EditText) v).addTextChangedListener(set3Watcher);
                    }
                    else /*if(!hasFocus)*/ {
                        ((EditText) v).removeTextChangedListener(set3Watcher);
                    }
                    break;
            }
        }
    };

    private TextWatcher set1Watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            double changedValue;

            double rate1 = 0.0;
            double rate2 = 0.0;

            double nnt;

            if(!s.toString().equalsIgnoreCase(""))
                changedValue = Double.parseDouble(s.toString());
            else
                changedValue = 0.0;

            if(s.hashCode() == rateEditText1.getText().hashCode())
            {
                rate1 = changedValue;
                rate2 = getValue(rateEditText2);
            }
            else if(s.hashCode() == rateEditText2.getText().hashCode())
            {
                rate1 = getValue(rateEditText1);
                rate2 = changedValue;
            }

            nnt = rate1 - rate2;
            nnt = 1 / nnt;

            rateTextView3.setText(String.format(Locale.getDefault(), "%.1f", nnt));

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private double getValue(EditText editText)
    {
        if(!editText.getText().toString().equalsIgnoreCase(""))
            return Double.parseDouble(editText.getText().toString());
        else
            return 0.0;
    }

    private TextWatcher set2Watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            int changedValue;

            int percentAge1 = 0;
            int percentAge2 = 0;

            double nnt;

            if(!s.toString().equalsIgnoreCase(""))
                changedValue = Integer.parseInt(s.toString());
            else
                changedValue = 0;

            if(s.hashCode() == rateEditText4.getText().hashCode())
            {
                percentAge1 = changedValue;
                percentAge2 = (int) getValue(rateEditText5);
            }
            else if(s.hashCode() == rateEditText5.getText().hashCode())
            {
                percentAge1 = (int) getValue(rateEditText4);
                percentAge2 = changedValue;
            }

            nnt = percentAge1 - percentAge2;
            nnt = 1 / nnt;

            rateTextView6.setText(String.format(Locale.getDefault(), "%.1f", nnt));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher set3Watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            double events1 = 0;
            double events2 = 0;
            double patients1 = 0;
            double patients2 = 0;

            double changedValue;
            double temp1;
            double temp2;

            double nnt;

            if(!s.toString().equalsIgnoreCase(""))
                changedValue = Double.parseDouble(s.toString());
            else
                changedValue = 0;

            if(s.hashCode() == rateEditText7.getText().hashCode())
            {
                events1 = changedValue;
                patients1 = getValue(rateEditText8);
                events2 = getValue(rateEditText9);
                patients2 = getValue(rateEditText10);
            }
            else if(s.hashCode() == rateEditText8.getText().hashCode())
            {
                events1 = getValue(rateEditText7);
                patients1 = changedValue;
                events2 = getValue(rateEditText9);
                patients2 = getValue(rateEditText10);
            }
            else if(s.hashCode() == rateEditText9.getText().hashCode())
            {
                events1 = getValue(rateEditText7);
                patients1 = getValue(rateEditText8);
                events2 = changedValue;
                patients2 = getValue(rateEditText10);
            }
            else if(s.hashCode() == rateEditText10.getText().hashCode())
            {
                events1 = getValue(rateEditText7);
                patients1 = getValue(rateEditText8);
                events2 = getValue(rateEditText9);
                patients2 = changedValue;
            }

            temp1 = events1 / patients1;
            temp2 = events2 / patients2;
            nnt = temp1 - temp2;
            nnt = 1 / nnt;

            rateTextView11.setText(String.format(Locale.getDefault(), "%.1f", nnt));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private EditText.OnEditorActionListener editTextsEditorActionListener = new EditText.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if(actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
            {
                InputMethodManager iMgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                iMgr.hideSoftInputFromWindow(nntParent.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                nntParent.requestFocus();
                //return true;
            }
            return false;
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_NNTScreen);
        super.onActivityCreated(savedInstanceState);
    }
}
