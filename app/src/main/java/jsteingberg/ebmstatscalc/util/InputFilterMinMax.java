package jsteingberg.ebmstatscalc.util;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    double min, max;

    /*public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }*/

    public InputFilterMinMax(){
    }

    InputFilterMinMax(String min, String max) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
            // Add the new string in
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());

            int decimalPosition = newVal.indexOf('.');
            if (decimalPosition != -1 && newVal.substring(decimalPosition + 1).length() > 1)
                return "";

            double input = Double.parseDouble(newVal);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
            return "";
        }
        return "";
    }

    boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}