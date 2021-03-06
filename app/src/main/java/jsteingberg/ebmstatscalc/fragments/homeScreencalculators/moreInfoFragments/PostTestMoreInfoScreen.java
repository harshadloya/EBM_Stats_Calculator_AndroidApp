package jsteingberg.ebmstatscalc.fragments.homeScreencalculators.moreInfoFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jsteingberg.ebmstatscalc.EBMCommunicator;
import jsteingberg.ebmstatscalc.R;

public class PostTestMoreInfoScreen extends Fragment
{
    private TextView postTestMoreInfoText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.posttest_moreinfo_screen, container, false);

        postTestMoreInfoText = view.findViewById(R.id.posttest_moreInfo_text);
        postTestMoreInfoText.setText(R.string.postTest_moreInfo_text);

        ((EBMCommunicator) getActivity()).setDrawerState(false, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_MoreInfoPostTest);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ((EBMCommunicator) getActivity()).setDrawerState(true, true);
        super.onDestroyView();
    }
}
