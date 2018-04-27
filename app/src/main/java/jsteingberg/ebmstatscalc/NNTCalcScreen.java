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
        assignListeners();

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

    private void assignListeners()
    {
        rateEditText1.addTextChangedListener(set1Watcher);
        rateEditText1.setOnEditorActionListener(onEditorActionListener);
    }

    private TextWatcher set1Watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            double rate1 = 0.0;
            double rate2 = 0.0;
            double nnt = 0.0;
            if(!s.toString().equalsIgnoreCase(""))
                rate1 = Double.parseDouble(s.toString());
            else
                rate1 = 0.0;

            if(!rateEditText2.getText().toString().equalsIgnoreCase(""))
                rate2 = Double.parseDouble(rateEditText2.getText().toString());
            else
                rate2 = 0.0;

            nnt = rate1 - rate2;
            nnt = 1 / nnt;

            rateTextView3.setText(String.format("%.1f", nnt));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void updateTextView3()
    {

    }

    private TextWatcher set2Watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

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

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private EditText.OnEditorActionListener onEditorActionListener = new EditText.OnEditorActionListener()
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
