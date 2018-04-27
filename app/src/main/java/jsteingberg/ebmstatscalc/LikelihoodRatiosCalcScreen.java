package jsteingberg.ebmstatscalc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jsteingberg.ebmstatscalc.util.HelperView;

public class LikelihoodRatiosCalcScreen extends Fragment
{
    private Button moreInfoBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.likelihood_ratios_screen, container, false);

        moreInfoBtn = (Button) view.findViewById(R.id.likelihood_moreInfoBtn);

        PostTestMoreInfoScreen postTestMoreInfoScreen = new PostTestMoreInfoScreen();
        HelperView helperView = new HelperView(postTestMoreInfoScreen, getFragmentManager(), "replaceWithSensSpecCalculatorScreen");
        moreInfoBtn.setOnClickListener(helperView.moreInfoBtnListener);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_PostTestScreens);
        super.onActivityCreated(savedInstanceState);
    }
}
