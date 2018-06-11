package jsteingberg.ebmstatscalc.fragments;

import android.view.View;

import jsteingberg.ebmstatscalc.util.HelperView;

public interface FragmentStructure {
    /**
     * Method where all components are initialized from the View
     *
     * @param view - View that has all the components
     */
    void initializeComponents(View view);

    /**
     * Method to set restrictions (if any needed) on components
     */
    void setFilters(HelperView helperView);

    /**
     * Method to assign listeners to components
     *
     * @param v - the view that may be needed for Recursion
     */
    void assignListeners(View v);
}
