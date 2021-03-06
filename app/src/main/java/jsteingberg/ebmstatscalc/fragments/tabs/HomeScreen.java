package jsteingberg.ebmstatscalc.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.LikelihoodRatiosCalcScreen;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.NNTCalcScreen;
import jsteingberg.ebmstatscalc.fragments.homeScreencalculators.SensSpecCalcScreen;
import jsteingberg.ebmstatscalc.util.UpdateScreen;

/**
 * Created by hloya on 3/29/2017.
 */

public class HomeScreen extends Fragment {
    private Button button1;
    private Button button2;
    private Button button3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen, container, false);

        button1 = view.findViewById(R.id.homeScreen_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NNTCalcScreen nntCalcScreen = new NNTCalcScreen();
                UpdateScreen.performScreenUpdateButtons(nntCalcScreen, getFragmentManager(), "replaceWithNNTCalculatorScreen", ((AppCompatActivity) getActivity()));
            }
        });

        button2 = view.findViewById(R.id.homeScreen_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensSpecCalcScreen sensSpecCalcScreen = new SensSpecCalcScreen();
                UpdateScreen.performScreenUpdateButtons(sensSpecCalcScreen, getFragmentManager(), "replaceWithSensSpecCalculatorScreen", ((AppCompatActivity) getActivity()));
            }
        });

        button3 = view.findViewById(R.id.homeScreen_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikelihoodRatiosCalcScreen likelihoodRatiosCalcScreen = new LikelihoodRatiosCalcScreen();
                UpdateScreen.performScreenUpdateButtons(likelihoodRatiosCalcScreen, getFragmentManager(), "replaceWithLikelihoodRatiosCalculatorScreen", ((AppCompatActivity) getActivity()));
            }
        });

/*        LikelihoodRatiosCalcScreen likelihoodRatiosCalcScreen = new LikelihoodRatiosCalcScreen();
        HelperView helperView = new HelperView(likelihoodRatiosCalcScreen, getFragmentManager(), "replaceWithLikelihoodRatiosCalculatorScreen", (AppCompatActivity) getActivity());
        button3.setOnClickListener(helperView.BtnClickListener);*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_HomeScreen);
        super.onActivityCreated(savedInstanceState);
    }
}