package jsteingberg.ebmstatscalc.fragments.homeScreencalculators.moreInfoFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jsteingberg.ebmstatscalc.R;

public class NNTMoreInfoScreen extends Fragment
{
    private TextView nntMoreInfoText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.nntmoreinfo_screen, container, false);

        nntMoreInfoText = view.findViewById(R.id.nnt_moreInfo_text);
        nntMoreInfoText.setMovementMethod(new ScrollingMovementMethod());
        nntMoreInfoText.setText(R.string.nnt_moreInfo_text);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_MoreInfoNNT);
        super.onActivityCreated(savedInstanceState);
    }
}
