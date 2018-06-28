package jsteingberg.ebmstatscalc.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import jsteingberg.ebmstatscalc.R;

public class AlertDialogFragment extends DialogFragment {
    // Use this instance of the interface to deliver action events
    private InfoDialogListener mListener;
    private AlertDialog.Builder alertDialogBuilder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        /*TextView textView = new TextView(getContext());
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);
        textView.setText(R.string.mainScreenAlertMessage);
        textView.setTextSize(20.0f);*/

        alertDialogBuilder = new AlertDialog.Builder(getActivity());

        //alertDialogBuilder.setView(textView)
        alertDialogBuilder.setMessage(R.string.mainScreenAlertMessage)
                .setNeutralButton(R.string.mainScreenAlertBtnName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the neutral button event back to the host activity
                        mListener.onDialogClick(AlertDialogFragment.this);
                    }
                });

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (InfoDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement InfoDialogListener");
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //super.onCancel(dialog);
        // Send the click event back to the host activity even when cancel event (tapping somewhere else on the screen/back button press) occurs
        mListener.onDialogClick(AlertDialogFragment.this);
    }

    public interface InfoDialogListener {
        void onDialogClick(DialogFragment dialog);
    }
}
