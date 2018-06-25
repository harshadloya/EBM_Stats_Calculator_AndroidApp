package jsteingberg.ebmstatscalc.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import jsteingberg.ebmstatscalc.EBMCommunicator;
import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.tabs.AboutAppScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.DisclaimerScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.HomeScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.MoreAppsScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.ReferencesScreen;
import jsteingberg.ebmstatscalc.util.UpdateScreen;

public class MainActivity extends AppCompatActivity implements EBMCommunicator {
    //private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 5555;
    //private static final int REQUEST_CODE_PICK_ACCOUNT = 5005;
    private  NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(drawerOnNavigationItemSelectedListener);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS))
            {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
            }
        }*/

        //getDetailsForDrawer(navigationView);
        updateUI(new HomeScreen(), R.color.mainScreenTabColor);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

/*
    Removing Intended Functionality as it goes beyond the need and scope of project
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST_GET_ACCOUNTS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getDetailsForDrawer(navigationView);
                }
                else
                {
                    Toast.makeText(this, R.string.accountSelectionCancelledToast, Toast.LENGTH_LONG).show();
                    //default img, name and email id will be displayed
                }
                break;
        }
    }

    private  void getDetailsForDrawer(NavigationView navigationView)
    {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT)
        {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK)
            {
                Account[] account = AccountManager.get(this).getAccountsByType("com.google");

                NavHeader nv = new NavHeader();
                nv.setProfileInDrawer(navigationView.getHeaderView(0), data);

            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, R.string.accountSelectionCancelledToast, Toast.LENGTH_LONG).show();
            }
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawerToggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.syncState();
        } else {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            drawerToggle.syncState();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    NavigationView.OnNavigationItemSelectedListener drawerOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return navigationFunctionality(item);
        }
    };

    private boolean navigationFunctionality(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);

            case R.id.navigation_home:
                HomeScreen homeScreen = new HomeScreen();
                updateUI(homeScreen, R.color.mainScreenTabColor);

                if(id == R.id.navigation_home)
                {
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                }
                return true;

            case R.id.nav_disclaimer:
                bottomNavigationView.getMenu().findItem(R.id.navigation_disclaimer).setChecked(true);

            case R.id.navigation_disclaimer:
                DisclaimerScreen disclaimerScreen = new DisclaimerScreen();
                updateUI(disclaimerScreen, R.color.disclaimerTabColor);

                if(id == R.id.navigation_disclaimer)
                {
                    navigationView.getMenu().findItem(R.id.nav_disclaimer).setChecked(true);
                }
                return true;

            case R.id.nav_aboutApp:
                bottomNavigationView.getMenu().findItem(R.id.navigation_aboutApp).setChecked(true);

            case R.id.navigation_aboutApp:
                AboutAppScreen aboutAppScreen = new AboutAppScreen();
                updateUI(aboutAppScreen, R.color.aboutAppTabColor);

                if(id == R.id.navigation_aboutApp)
                {
                    navigationView.getMenu().findItem(R.id.nav_aboutApp).setChecked(true);
                }
                return true;

            case R.id.nav_references:
                bottomNavigationView.getMenu().findItem(R.id.navigation_references).setChecked(true);

            case R.id.navigation_references:
                ReferencesScreen referencesScreen = new ReferencesScreen();
                updateUI(referencesScreen, R.color.referencesTabColor);
                if(id == R.id.navigation_references)
                {
                    navigationView.getMenu().findItem(R.id.nav_references).setChecked(true);
                }
                return true;

            case R.id.nav_moreApps:
                bottomNavigationView.getMenu().findItem(R.id.navigation_moreApps).setChecked(true);

            case R.id.navigation_moreApps:
                MoreAppsScreen moreAppsScreen = new MoreAppsScreen();
                updateUI(moreAppsScreen, R.color.moreAppsTabColor);

                if(id == R.id.navigation_moreApps)
                {
                    navigationView.getMenu().findItem(R.id.nav_moreApps).setChecked(true);
                }
                return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return navigationFunctionality(item);
        }
    };

    private void updateUI(Fragment fragment, int menuColor) {
        UpdateScreen.performScreenUpdateTabs(fragment, getSupportFragmentManager());
        navigationView.setBackgroundColor(getResources().getColor(menuColor));
        bottomNavigationView.setBackgroundColor(getResources().getColor(menuColor));
    }
}
