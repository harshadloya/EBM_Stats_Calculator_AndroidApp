package jsteingberg.ebmstatscalc.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

public class HelperView
{
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private String backStackName;

    public HelperView(Fragment frag, FragmentManager fMgr, String bsName)
    {
        fragment = frag;
        fragmentManager = fMgr;
        backStackName = bsName;
    }

    public View.OnClickListener moreInfoBtnListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            UpdateScreen.performScreenUpdateButtons(fragment, fragmentManager, backStackName);
        }
    };

    public static void setFiltersEditText(EditText editText, String min, String max)
    {
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(min, max)});
    }

    public static void setFiltersEditText_2Decimals(EditText editText, String min, String max)
    {
        editText.setFilters(new InputFilter[]{new InputFilterMinMax_2Decimals(min, max)});
    }
}