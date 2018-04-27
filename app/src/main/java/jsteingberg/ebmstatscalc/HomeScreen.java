package jsteingberg.ebmstatscalc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        button1 = (Button) view.findViewById(R.id.homeScreen_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NNTCalcScreen nntCalcScreen = new NNTCalcScreen();
                UpdateScreen.performScreenUpdateButtons(nntCalcScreen, getFragmentManager(), "replaceWithNNTCalculatorScreen");
            }
        });

        button2 = (Button) view.findViewById(R.id.homeScreen_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensSpecCalcScreen sensSpecCalcScreen = new SensSpecCalcScreen();
                UpdateScreen.performScreenUpdateButtons(sensSpecCalcScreen, getFragmentManager(), "replaceWithSensSpecCalculatorScreen");
            }
        });

        button3 = (Button) view.findViewById(R.id.homeScreen_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikelihoodRatiosCalcScreen likelihoodRatiosCalcScreen = new LikelihoodRatiosCalcScreen();
                UpdateScreen.performScreenUpdateButtons(likelihoodRatiosCalcScreen, getFragmentManager(), "replaceWithLikelihoodRatiosCalculatorScreen");
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_HomeScreen);
        super.onActivityCreated(savedInstanceState);
    }

}