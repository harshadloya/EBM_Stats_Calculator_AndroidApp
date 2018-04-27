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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import jsteingberg.ebmstatscalc.util.HelperView;

public class SensSpecCalcScreen extends Fragment
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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sens_spec_screen, container, false);

        initializeComponents(view);
        setFilters();
        assignListeners();


        PostTestMoreInfoScreen postTestMoreInfoScreen = new PostTestMoreInfoScreen();
        HelperView helperView = new HelperView(postTestMoreInfoScreen, getFragmentManager(), "replaceWithSensSpecCalculatorScreen");
        moreInfoBtn.setOnClickListener(helperView.moreInfoBtnListener);

        return view;
    }

    private void initializeComponents(View view)
    {
        sensSpecParent = (ConstraintLayout) view.findViewById(R.id.sensspeccalcparent);

        moreInfoBtn = (Button) view.findViewById(R.id.sensspec_moreInfoBtn);

        popuToggleButton1 = (ToggleButton) view.findViewById(R.id.sensspec_populationValue1);
        popuToggleButton2 = (ToggleButton) view.findViewById(R.id.sensspec_populationValue2);
        popuToggleButton3 = (ToggleButton) view.findViewById(R.id.sensspec_populationValue3);

        preTestPercent = (EditText) view.findViewById(R.id.sensspec_preTestPercentVal);
        preTestBar = (SeekBar) view.findViewById(R.id.sensspec_seekBar);

        sensitivityPercent = (EditText) view.findViewById(R.id.sensspec_sensitivityPercentVal);
        specificityPercent = (EditText) view.findViewById(R.id.sensspec_specificityPercentVal);

    }

    private void assignListeners()
    {
        popuToggleButton1.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);
        popuToggleButton2.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);
        popuToggleButton3.setOnCheckedChangeListener(popuToggleBtnCheckedChangeListener);

        preTestPercent.setOnFocusChangeListener(preTestOnFocusChangeListener);

        preTestPercent.setOnEditorActionListener(onEditorActionListener);

        preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);
    }

    private void setFilters()
    {
        HelperView.setFiltersEditText(preTestPercent, "0.0", "100.0");
        HelperView.setFiltersEditText(sensitivityPercent, "0.0", "100.0");
        HelperView.setFiltersEditText(specificityPercent, "0.0", "100.0");
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
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            //preTestPercent.removeTextChangedListener(preTestWatcher);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private TextWatcher preTestWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

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
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


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

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_PostTestScreens);
        super.onActivityCreated(savedInstanceState);
    }

}
