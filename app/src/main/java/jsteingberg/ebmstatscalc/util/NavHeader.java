//package jsteingberg.ebmstatscalc.util;
//
//import android.accounts.Account;
//import android.accounts.AccountManager;
//import android.content.Intent;
//import android.support.v4.app.Fragment;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//
//import jsteingberg.ebmstatscalc.R;
//
///**
// * Created by hloya on 2/17/2018.
// */
//
//public class NavHeader extends Fragment
//{
//    private ImageView imageView;
//    private TextView textView1;
//    private TextView textView2;
//
//    public void setProfileInDrawer(View view, Intent data)
//    {
//        imageView = view.findViewById(R.id.profileImage);
//        textView1 = view.findViewById(R.id.userName);
//        textView2 = view.findViewById(R.id.userEmail);
//
//        /*textView1.setText(data.getStringExtra(AccountManager.KEY_USERDATA));
//        textView2.setText(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));*/
//
//
//        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount((Activity) view.getContext());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//            imageView.setImageURI(personPhoto);
//            textView1.setText(personName);
//            textView2.setText(personEmail);
//        }*/
//
//    }
//}
