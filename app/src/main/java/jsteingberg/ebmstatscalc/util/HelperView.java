package jsteingberg.ebmstatscalc.util;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.widget.EditText;

public class HelperView extends AppCompatActivity
{
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private String backStackName;
    private AppCompatActivity activity;
    SharedPreferences sharedPreferences;

    public HelperView() {
    }

/*    public HelperView(Fragment frag, FragmentManager fMgr, String bsName, AppCompatActivity appCompatActivity)
    {
        fragment = frag;
        fragmentManager = fMgr;
        backStackName = bsName;
        activity = appCompatActivity;
    }

    public View.OnClickListener BtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            *//*sharedPreferences = activity.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
            //sharedPrefEditor.putString("", );*//*

            sharedPreferences = activity.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("TextView3", rateTextView3.getText().toString()).commit();
            sharedPreferences.edit().putString("TextView6", rateTextView6.getText().toString()).commit();
            sharedPreferences.edit().putString("TextView11", rateTextView11.getText().toString()).commit();

            UpdateScreen.performScreenUpdateButtons(fragment, fragmentManager, backStackName, activity);
        }
    };*/

    public static void setFiltersEditText(EditText editText, String min, String max)
    {
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(min, max)});
    }

    public static void setFiltersEditText_2Decimals(EditText editText, String min, String max)
    {
        editText.setFilters(new InputFilter[]{new InputFilterMinMax_2Decimals(min, max)});
    }
}