package jsteingberg.ebmstatscalc.fragments;

import android.view.View;

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
    void setFilters();

    /**
     * Method to assign listeners to components
     *
     * @param v - the view that may be needed for Recursion
     */
    void assignListeners(View v);
}
