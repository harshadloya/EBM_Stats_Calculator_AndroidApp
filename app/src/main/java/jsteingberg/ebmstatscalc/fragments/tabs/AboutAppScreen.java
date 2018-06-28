package jsteingberg.ebmstatscalc.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jsteingberg.ebmstatscalc.R;

/**
 * Created by hloya on 3/29/2017.
 */

public class AboutAppScreen extends Fragment {
    private TextView mTextMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aboutapp_screen, container, false);

        mTextMessage = view.findViewById(R.id.aboutapp_message);
        mTextMessage.setText(R.string.aboutAppScreen_text);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.actionbar_AboutAppScreen);
        super.onActivityCreated(savedInstanceState);
    }
}
