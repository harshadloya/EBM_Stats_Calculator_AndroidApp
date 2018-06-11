package jsteingberg.ebmstatscalc.fragments.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jsteingberg.ebmstatscalc.R;

/**
 * Created by hloya on 3/29/2017.
 */

public class MoreAppsScreen extends Fragment {
    private Button button;
    private Button button2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moreapps_screen, container, false);
        button = view.findViewById(R.id.moreApps_Button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://media.wix.com/ugd/cd92db_e325a0c13e944ff3bca79a7312087629.doc?dn=Apps%20list%20for%20iPhone.doc");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button2 = view.findViewById(R.id.moreApps_Button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Joshua+Steinberg+MD")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Joshua+Steinberg+MD")));
                }
            }
        });

        return view;
    }

}
