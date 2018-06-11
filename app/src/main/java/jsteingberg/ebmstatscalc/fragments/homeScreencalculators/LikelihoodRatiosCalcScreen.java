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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.FragmentStructure;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.moreInfoFragments.PostTestMoreInfoScreen;
import jsteingberg.ebmstatscalc.util.HelperView;

public class LikelihoodRatiosCalcScreen extends Fragment implements FragmentStructure {
    private ConstraintLayout likelihoodParent;

    private Button moreInfoBtn;

    private SeekBar preTestBar;
    private EditText preTestEditText;

    private EditText likelihoodPosEditText;

    private EditText likelihoodNegEditText;

    private TextView postTestPos;
    private TextView postTestNeg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.likelihood_ratios_screen, container, false);

        initializeComponents(view);

        PostTestMoreInfoScreen postTestMoreInfoScreen = new PostTestMoreInfoScreen();
        HelperView helperView = new HelperView(postTestMoreInfoScreen, getFragmentManager(), "replaceWithLikelihoodCalculatorScreen");
        moreInfoBtn.setOnClickListener(helperView.moreInfoBtnListener);

        setFilters(helperView);
        assignListeners(view);

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
    }

    @Override
    public void setFilters(HelperView helperView) {
        helperView.setFiltersEditText(preTestEditText, "0.0", "100.0");
        helperView.setFiltersEditText(likelihoodPosEditText, "0.0", "10.0");
        helperView.setFiltersEditText(likelihoodNegEditText, "0.0", "1.0");
    }

    @Override
    public void assignListeners(View v) {
        preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);

        preTestEditText.setOnFocusChangeListener(preTestOnFocusChangeListener);
        preTestEditText.setOnEditorActionListener(onEditorActionListener);

    }

    private View.OnFocusChangeListener preTestOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                preTestBar.setOnSeekBarChangeListener(null);
                preTestEditText.addTextChangedListener(preTestWatcher);

            } else {
                preTestEditText.removeTextChangedListener(preTestWatcher);
                preTestBar.setOnSeekBarChangeListener(preTestSeekBarProgressListener);

            }
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

    private SeekBar.OnSeekBarChangeListener preTestSeekBarProgressListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double value = progress / 10.0;
            preTestEditText.setText("" + value);

            updateOtherFields();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void updateOtherFields() {
        float preTestOdds;
        float postTestProbPos;
        float postTestProbNeg;

        // get the values of each independent variable and put 'em in primitive float variables
        float preTestProb = Float.parseFloat(preTestEditText.getText().toString()) / 100;     // includes conversion of % to rate
        float likelihoodRatioPos = Float.parseFloat(likelihoodPosEditText.getText().toString());
        float likelihoodRatioNeg = Float.parseFloat(likelihoodNegEditText.getText().toString());

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
        postTestPos.setText(String.format("%.1f", postTestProbPos * 100));
        postTestNeg.setText(String.format("%.1f", postTestProbNeg * 100));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_PostTestScreens);
        super.onActivityCreated(savedInstanceState);
    }
}