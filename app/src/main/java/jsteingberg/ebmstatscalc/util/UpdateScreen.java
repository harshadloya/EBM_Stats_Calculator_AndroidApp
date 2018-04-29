package jsteingberg.ebmstatscalc.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import jsteingberg.ebmstatscalc.R;

public class UpdateScreen
{

    /**
     * Updates the UI on the screen when the tabs are changed
     * @param fragment - new fragment that will be displayed on the UI
     * @param fragmentManager - fragment manager needed to perform the update
     */
    public static void performScreenUpdateTabs(Fragment fragment, FragmentManager fragmentManager)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.contentFrag, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Updates the UI on the screen when a button on a tab is pressed
     * @param fragment - new fragment that will be displayed on the UI
     * @param fragmentManager - fragment manager needed to perform the update
     * @param backStackStateName - name of the fragment being added to the stack
     */
    public static void performScreenUpdateButtons(Fragment fragment, FragmentManager fragmentManager, String backStackStateName)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrag, fragment);
        fragmentTransaction.addToBackStack(backStackStateName);
        fragmentTransaction.commit();
    }
}
