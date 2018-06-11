package jsteingberg.ebmstatscalc.fragments.homeScreencalculators;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.FragmentStructure;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.moreInfoFragments.PostTestMoreInfoScreen;
import jsteingberg.ebmstatscalc.util.HelperView;

public class SensSpecCalcScreen extends Fragment implements FragmentStructure
{
    private ConstraintLayout sensSpecParent;
    private Button moreInfoBtn;
    private ToggleButton popuToggleButton1;
    private ToggleButton popuToggleButton2;
    private ToggleButton popuToggleButton3;
    private EditText preTestPercent;
    private SeekBar preTestBar;

    private EditText sensitivityPercent;
    private EditText specificityPercent;

    private TextView trueP;
    private TextView falseP;
    private TextView testP;
    private TextView falseN;
    private TextView trueN;
    private TextView testN;
    private TextView dzP;
    private TextView dzN;
    private TextView total;

    private TextView pPV;
    private TextView nPV;
    private TextView postTestPos;
    private TextView postTestNeg;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sens_spec_screen, container, false);

        initializeComponents(view);

        PostTestMoreInfoScreen postTestMoreInfoScreen = new PostTestMoreInfoScreen();
        HelperView helperView = new HelperView(postTestMoreInfoScreen, getFragmentManager(), "replaceWithSensSpecCalculatorScreen");
        moreInfoBtn.setOnClickListener(helperView.moreInfoBtnListener);

        setFilters(helperView);
        assignListeners(view);

        return view;
    }

    @Override
    public void initializeComponents(View view)
    {
        sensSpecParent = view.findViewById(R.id.sensspeccalcparent);

        moreInfoBtn = view.findViewById(R.id.sensspec_moreInfoBtn);

        popuToggleButton1 = view.findViewById(R.id.sensspec_populationValue1);
        popuToggleButton2 = view.findViewById(R.id.sensspec_populationValue2);
        popuToggleButton3 = view.findViewById(R.id.sensspec_populationValue3);

        preTestPercent = view.findViewById(R.id.sensspec_preTestPercentVal);
        preTestBar = view.findViewById(R.id.sensspec_seekBar);

        sensitivityPercent = view.findViewById(R.id.sensspec_sensitivityPercentVal);
        specificityPercent = view.findViewById(R.id.sensspec_specificityPercentVal);

        trueP = view.findViewById(R.id.sensspec_testPosPosVal);
        falseP = view.findViewById(R.id.sensspec_testPosNegVal);
        testP = view.findViewById(R.id.sensspec_testPosTotalVal);
        falseN = view.findViewById(R.id.sensspec_testNegPosVal);
        trueN = view.findViewById(R.id.sensspec_testNegNegVal);
        testN = view.findViewById(R.id.sensspec_testNegTotalVal);
        dzP = view.findViewById(R.id.sensspec_DzP);
        dzN = view.findViewById(R.id.sensspec_DzN);
        total = view.findViewById(R.id.sensspec_totalVal);

        pPV = view.findViewById(R.id.sensspec_posPredictivePercentVal);
        nPV = view.findViewById(R.id.sensspec_negPredictivePercentVal);
        postTestPos = view.findViewById(R.id.sensspec_posPostTestProbPercentVal);
        postTestNeg = view.findViewById(R.id.sensspec_negPostTestProbPercentVal);
    }

    @Override
    public void assignListeners(View view)
    {
        //Listeners to Make sure only 1 value is selected at a time
        popuToggleButton1.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);
        popuToggleButton2.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);
        popuToggleButton3.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);

        preTestPercent.setOnFocusChangeListener(preTestOnFocusChangeListener);
        preTestPercent.setOnEditorActionListener(onEditorActionListener);

        preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);

        sensitivityPercent.setOnFocusChangeListener(sensSpecOnFocusChangeListener);
        sensitivityPercent.setOnEditorActionListener(onEditorActionListener);
        specificityPercent.setOnFocusChangeListener(sensSpecOnFocusChangeListener);
        specificityPercent.setOnEditorActionListener(onEditorActionListener);
    }

    @Override
    public void setFilters(HelperView helperView)
    {
        helperView.setFiltersEditText(preTestPercent, "0.0", "100.0");
        helperView.setFiltersEditText(sensitivityPercent, "0.0", "100.0");
        helperView.setFiltersEditText(specificityPercent, "0.0", "100.0");
    }

    private View.OnFocusChangeListener preTestOnFocusChangeListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if(hasFocus)
            {
                preTestBar.setOnSeekBarChangeListener(null);
                preTestPercent.addTextChangedListener(preTestWatcher);

            }
            else
            {
                preTestPercent.removeTextChangedListener(preTestWatcher);
                preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);

            }
        }
    };

    private View.OnFocusChangeListener sensSpecOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && v.getId() == R.id.sensspec_sensitivityPercentVal) {
                sensitivityPercent.addTextChangedListener(sensSpecWatcher);
            } else if (!hasFocus && v.getId() == R.id.sensspec_sensitivityPercentVal) {
                sensitivityPercent.removeTextChangedListener(sensSpecWatcher);
            } else if (hasFocus && v.getId() == R.id.sensspec_specificityPercentVal) {
                specificityPercent.addTextChangedListener(sensSpecWatcher);
            } else if (!hasFocus && v.getId() == R.id.sensspec_specificityPercentVal) {
                specificityPercent.removeTextChangedListener(sensSpecWatcher);
            }
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
                iMgr.hideSoftInputFromWindow(preTestPercent.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                sensSpecParent.requestFocus();
                //return true;
            }
            return false;
        }
    };

    private SeekBar.OnSeekBarChangeListener preTestSeekBarProgressListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            double value = progress / 10.0;
            preTestPercent.setText(""+value);

            updateAllFields();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private TextWatcher preTestWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.length()>0 && !s.toString().equals("."))
            {
                int x = (int) (Double.parseDouble(s.toString()) * 10);
                preTestBar.setProgress(x, true);
            }
            else
                preTestBar.setProgress(0, true);

            updateAllFields();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private TextWatcher sensSpecWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0 && !s.toString().equals(".")) {
                //double x = Double.parseDouble(s.toString());
                updateAllFields();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void updateAllFields() {
        double populationVal = 0.0;

        double truePVal;
        double falsePVal;
        double testPVal;

        double falseNVal;
        double trueNVal;
        double testNVal;

        double dzPValue;
        double dzNValue;

        double pPVVal;
        double nPVVal;

        double postTestNegVal;

        if (popuToggleButton1.isChecked()) {
            populationVal = Double.parseDouble(popuToggleButton1.getText().toString());
            total.setText("" + populationVal);
        } else if (popuToggleButton2.isChecked()) {
            populationVal = Double.parseDouble(popuToggleButton2.getText().toString().replaceAll(",", ""));
            total.setText("" + populationVal);
        } else if (popuToggleButton3.isChecked()) {
            populationVal = Double.parseDouble(popuToggleButton3.getText().toString().replaceAll(",", ""));
            total.setText("" + populationVal);
        }

        double preTestVal = 0.0;
        double sensitivityVal = 0.0;
        double specificityVal = 0.0;

        if (!preTestPercent.getText().toString().equalsIgnoreCase("")) {
            preTestVal = Double.parseDouble(preTestPercent.getText().toString());
        }
        if (!sensitivityPercent.getText().toString().equalsIgnoreCase("")) {
            sensitivityVal = Double.parseDouble(sensitivityPercent.getText().toString());
        }
        if (!specificityPercent.getText().toString().equalsIgnoreCase("")) {
            specificityVal = Double.parseDouble(specificityPercent.getText().toString());
        }

        dzPValue = populationVal * (preTestVal / 100);
        dzP.setText(String.format("%.1f", dzPValue));

        dzNValue = populationVal - dzPValue;
        dzN.setText(String.format("%.1f", dzNValue));

        truePVal = dzPValue * (sensitivityVal / 100);
        trueP.setText(String.format("%.1f", truePVal));

        falseNVal = dzPValue - truePVal;
        falseN.setText(String.format("%.1f", falseNVal));

        trueNVal = dzNValue * (specificityVal / 100);
        trueN.setText(String.format("%.1f", trueNVal));

        falsePVal = dzNValue - trueNVal;
        falseP.setText(String.format("%.1f", falsePVal));

        testPVal = truePVal + falsePVal;
        testP.setText(String.format("%.1f", testPVal));

        testNVal = falseNVal + trueNVal;
        testN.setText(String.format("%.1f", testNVal));

        pPVVal = 100 * (truePVal / testPVal);
        pPV.setText(String.format("%.1f", pPVVal));

        postTestPos.setText(String.format("%.1f", pPVVal));

        nPVVal = 100 * (trueNVal / testNVal);
        nPV.setText(String.format("%.1f", nPVVal));

        postTestNegVal = 100 - nPVVal;
        postTestNeg.setText(String.format("%.1f", postTestNegVal));
    }


    private ToggleButton.OnCheckedChangeListener popuToggleBtnCheckedChangeListener = new ToggleButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if(isChecked && buttonView.getId() == R.id.sensspec_populationValue1)
            {
                popuToggleButton2.setChecked(false);
                popuToggleButton3.setChecked(false);
            }
            else if(isChecked && buttonView.getId() == R.id.sensspec_populationValue2)
            {
                popuToggleButton1.setChecked(false);
                popuToggleButton3.setChecked(false);
            }
            else if(isChecked && buttonView.getId() == R.id.sensspec_populationValue3)
            {
                popuToggleButton1.setChecked(false);
                popuToggleButton2.setChecked(false);
            }
            updateAllFields();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_PostTestScreens);
        super.onActivityCreated(savedInstanceState);
    }
}