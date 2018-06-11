package jsteingberg.ebmstatscalc.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jsteingberg.ebmstatscalc.R;
import jsteingberg.ebmstatscalc.fragments.tabs.AboutAppScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.DisclaimerScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.HomeScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.MoreAppsScreen;
import jsteingberg.ebmstatscalc.fragments.tabs.ReferencesScreen;
import jsteingberg.ebmstatscalc.util.NavHeader;
import jsteingberg.ebmstatscalc.util.UpdateScreen;

public class MainActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 5555;
    private  NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(drawerOnNavigationItemSelectedListener);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED)
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
        }

        getDetailsForDrawer(navigationView);
        updateUI(new HomeScreen(), R.color.mainScreenTabColor);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

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
                    //set default img name n email id
                }
                break;
        }
    }

    private  void getDetailsForDrawer(NavigationView navigationView)
    {
        //AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        //Account[] list = manager.getAccounts();
        Account[] list = AccountManager.get(this).getAccounts();
        String gmail = null;

        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                gmail = account.name;
                break;
            }
        }

        NavHeader nv = new NavHeader();
        //nv.setProfileInDrawer(navigationView.getHeaderView(0));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    private void updateUI(Fragment fragment, int menuColor)
    {
        UpdateScreen.performScreenUpdateTabs(fragment, getSupportFragmentManager());
        navigationView.setBackgroundColor(getResources().getColor(menuColor));
        bottomNavigationView.setBackgroundColor(getResources().getColor(menuColor));
    }

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
}
