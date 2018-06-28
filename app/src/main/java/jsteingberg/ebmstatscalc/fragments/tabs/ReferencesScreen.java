package jsteingberg.ebmstatscalc.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jsteingberg.ebmstatscalc.R;

/**
 * Created by hloya on 3/29/2017.
 */

public class ReferencesScreen extends Fragment {
    private TextView mTextMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.references_screen, container, false);

        mTextMessage = view.findViewById(R.id.ref_message);
        mTextMessage.setText(R.string.refScreen_text);
        mTextMessage.setMovementMethod(LinkMovementMethod.getInstance());

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.actionbar_ReferencesScreen);
        super.onActivityCreated(savedInstanceState);
    }
}
