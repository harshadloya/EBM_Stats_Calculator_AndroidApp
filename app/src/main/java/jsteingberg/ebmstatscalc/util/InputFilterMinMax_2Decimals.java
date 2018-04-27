package jsteingberg.ebmstatscalc.util;

import android.text.Spanned;

public class InputFilterMinMax_2Decimals extends InputFilterMinMax {

    public InputFilterMinMax_2Decimals(String min, String max) {
        super(min, max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
            // Add the new string in
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());

            int decimalPosition = newVal.indexOf('.');
            if(newVal.substring(decimalPosition+1).length() > 2)
                return "";

            double input = Double.parseDouble(newVal);
            if (isInRange(min, max, input))
                return null;
        }
        catch (NumberFormatException nfe) {
        }
        return "";
    }
}