package jsteingberg.ebmstatscalc.fragments.homeScreencalculators;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import jsteingberg.ebmstatscalc.EBMCommunicator;
import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.FragmentStructure;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.moreInfoFragments.PostTestMoreInfoScreen;
import jsteingberg.ebmstatscalc.util.UpdateScreen;

import static jsteingberg.ebmstatscalc.util.HelperView.setFiltersEditText;

public class LikelihoodRatiosCalcScreen extends Fragment implements FragmentStructure {
    private ConstraintLayout likelihoodParent;

    private Button moreInfoBtn;

    private SeekBar preTestBar;
    private EditText preTestEditText;

    private EditText likelihoodPosEditText;

    private EditText likelihoodNegEditText;

    private TextView postTestPos;
    private TextView postTestNeg;

    private SharedPreferences sharedPreferences;
    private View.OnClickListener BtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sharedPreferences.edit().putString("Check", "True").commit();
            sharedPreferences.edit().putString("Pretest", preTestEditText.getText().toString()).commit();
            sharedPreferences.edit().putString("LR+", likelihoodPosEditText.getText().toString()).commit();
            sharedPreferences.edit().putString("LR-", likelihoodNegEditText.getText().toString()).commit();

            PostTestMoreInfoScreen postTestMoreInfoScreen = new PostTestMoreInfoScreen();
            UpdateScreen.performScreenUpdateButtons(postTestMoreInfoScreen, getFragmentManager(), "replaceWithLikelihoodCalculatorMoreInfoScreen", (AppCompatActivity) getActivity());
        }
    };
    private TextWatcher preTestWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0 && !s.toString().equals(".")) {
                int x = (int) (Double.parseDouble(s.toString()) * 10);
                preTestBar.setProgress(x, true);
            } else
                preTestBar.setProgress(0, true);

            updateOtherFields();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.likelihood_ratios_screen, container, false);

        initializeComponents(view);
        setFilters();
        assignListeners(view);

        updateFieldsIfNeeded();

        ((EBMCommunicator) getActivity()).setDrawerState(false, false);

        return view;
    }

    @Override
    public void initializeComponents(View view) {
        likelihoodParent = view.findViewById(R.id.likelihoodcalc_parent);

        moreInfoBtn = view.findViewById(R.id.likelihood_moreInfoBtn);

        preTestBar = view.findViewById(R.id.likelihood_probabilitySeekBar);
        preTestEditText = view.findViewById(R.id.likelihood_preTestPercentVal);

        likelihoodPosEditText = view.findViewById(R.id.likelihood_posRatioVal);

        likelihoodNegEditText = view.findViewById(R.id.likelihood_negRatioVal);

        postTestPos = view.findViewById(R.id.likelihood_testPosVal);
        postTestNeg = view.findViewById(R.id.likelihood_testNegVal);

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.likelihood_preference_file_key), Context.MODE_PRIVATE);
    }

    @Override
    public void setFilters() {
        setFiltersEditText(preTestEditText, "0.0", "100.0");
        //helperView.setFiltersEditText(likelihoodPosEditText, "0.0", "999.9");
        //helperView.setFiltersEditText(likelihoodNegEditText, "0.0000", "10.0000");
    }

    @Override
    public void assignListeners(View v) {
        preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);

        preTestEditText.setOnFocusChangeListener(preTestOnFocusChangeListener);
        preTestEditText.setOnEditorActionListener(onEditorActionListener);

        likelihoodPosEditText.setOnFocusChangeListener(preTestOnFocusChangeListener);
        likelihoodPosEditText.setOnEditorActionListener(onEditorActionListener);
        likelihoodNegEditText.setOnFocusChangeListener(preTestOnFocusChangeListener);
        likelihoodNegEditText.setOnEditorActionListener(onEditorActionListener);

        moreInfoBtn.setOnClickListener(BtnClickListener);
    }

    private TextWatcher likelihoodPosNegWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateOtherFields();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private SeekBar.OnSeekBarChangeListener preTestSeekBarProgressListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double value = progress / 10.0;
            preTestEditText.setText(String.format(Locale.getDefault(), "%.1f", value));


            updateOtherFields();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private View.OnFocusChangeListener preTestOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && v.getId() == R.id.likelihood_preTestPercentVal) {
                preTestBar.setOnSeekBarChangeListener(null);
                preTestEditText.addTextChangedListener(preTestWatcher);

            } else if (!hasFocus && v.getId() == R.id.likelihood_preTestPercentVal) {
                preTestEditText.removeTextChangedListener(preTestWatcher);
                preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);
            }
            if (hasFocus && v.getId() == R.id.likelihood_posRatioVal) {
                likelihoodPosEditText.addTextChangedListener(likelihoodPosNegWatcher);
            } else if (!hasFocus && v.getId() == R.id.likelihood_posRatioVal) {
                likelihoodPosEditText.removeTextChangedListener(likelihoodPosNegWatcher);
            }
            if (hasFocus && v.getId() == R.id.likelihood_negRatioVal) {
                likelihoodNegEditText.addTextChangedListener(likelihoodPosNegWatcher);
            } else if (!hasFocus && v.getId() == R.id.likelihood_negRatioVal) {
                likelihoodNegEditText.removeTextChangedListener(likelihoodPosNegWatcher);
            }
        }
    };


    private EditText.OnEditorActionListener onEditorActionListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager iMgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                iMgr.hideSoftInputFromWindow(preTestEditText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                likelihoodParent.requestFocus();
                //return true;
            }
            return false;
        }
    };

    private void updateFieldsIfNeeded() {
        String check = sharedPreferences.getString("Check", "");
        if (!check.equalsIgnoreCase("")) {
            preTestEditText.setText(sharedPreferences.getString("Pretest", ""));
            likelihoodPosEditText.setText(sharedPreferences.getString("LR+", ""));
            likelihoodNegEditText.setText(sharedPreferences.getString("LR-", ""));

            updateOtherFields();
            sharedPreferences.edit().clear().apply();
        }
    }

    private void updateOtherFields() {
        float preTestOdds;
        float postTestProbPos;
        float postTestProbNeg;
        float preTestProb;
        float likelihoodRatioPos;
        float likelihoodRatioNeg;

        // get the values of each independent variable and put 'em in primitive float variables

        String preTestProbStr = preTestEditText.getText().toString();
        if (preTestProbStr.equalsIgnoreCase("") || preTestProbStr.equalsIgnoreCase(".")) {
            preTestProb = 0;
        } else {
            preTestProb = Float.parseFloat(preTestProbStr) / 100;     // includes conversion of % to rate
        }

        String likelihoodPosStr = likelihoodPosEditText.getText().toString();
        if (likelihoodPosStr.equalsIgnoreCase("") || likelihoodPosStr.equalsIgnoreCase(".")) {
            likelihoodRatioPos = 0;
        } else {
            likelihoodRatioPos = Float.parseFloat(likelihoodPosStr);
        }

        String likelihoodNegStr = likelihoodNegEditText.getText().toString();
        if (likelihoodNegStr.equalsIgnoreCase("") || likelihoodNegStr.equalsIgnoreCase(".")) {
            likelihoodRatioNeg = 0;
        } else {
            likelihoodRatioNeg = Float.parseFloat(likelihoodNegStr);
        }


        // The math now
        // the math benefits from converting pretest prob to pretest odds so do it separately for clarity
        preTestOdds = preTestProb / (1 - preTestProb);


        // implement following lengthy equations
        // PosttestProb(+) = (PretestOdds × LR+) / (1 + (PretestOdds × LR+))
        // PosttestProb(-) = (PretestOdds × LR-) / (1 + (PretestOdds × LR-))
        postTestProbPos = (preTestOdds * likelihoodRatioPos) / (1 + (preTestOdds * likelihoodRatioPos));
        postTestProbNeg = (preTestOdds * likelihoodRatioNeg) / (1 + (preTestOdds * likelihoodRatioNeg));

        // now that we have (hopefully) the posttest probabilities, output them to the display
        // the posttest probabilities are rates (betw 0.0-1.0) and need to be multiplied by 100 to become percentages
        postTestPos.setText(String.format(Locale.getDefault(), "%.1f", postTestProbPos * 100));
        postTestNeg.setText(String.format(Locale.getDefault(), "%.1f", postTestProbNeg * 100));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_PostTestScreens);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ((EBMCommunicator) getActivity()).setDrawerState(true, true);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (outState != null) {
            outState.putString("Pretest", preTestEditText.getText().toString());
            outState.putString("LR+", likelihoodPosEditText.getText().toString());
            outState.putString("LR-", likelihoodNegEditText.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            preTestEditText.setText(savedInstanceState.getString("Pretest"));
            likelihoodPosEditText.setText(savedInstanceState.getString("LR+"));
            likelihoodNegEditText.setText(savedInstanceState.getString("LR-"));

            //Update Remaining Fields based on the above 3 values
            updateOtherFields();
        }
        super.onViewStateRestored(savedInstanceState);
    }
}
