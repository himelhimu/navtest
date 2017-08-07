package test.android.sabbir.navdrawer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.activites.RegisterActivity;
import test.android.sabbir.navdrawer.application.MyApplication;
import test.android.sabbir.navdrawer.databases.DatabaseHelper;
import test.android.sabbir.navdrawer.fragments.FavoritesFragment;
import test.android.sabbir.navdrawer.fragments.GitHubFragment;
import test.android.sabbir.navdrawer.fragments.MusicLibraryFragment;
import test.android.sabbir.navdrawer.fragments.NasaMainFragment;
import test.android.sabbir.navdrawer.fragments.SavedFragment;
import test.android.sabbir.navdrawer.fragments.SettingsFragment;
import test.android.sabbir.navdrawer.fragments.ShareFragment;
import test.android.sabbir.navdrawer.fragments.StackOverflowFragment;

/**
* @author sabbir
* */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     @BindView(R.id.log_in_layout)
     RelativeLayout mRelativeLayout;
    MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myApplication=MyApplication.getInstance();
        checkForPermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Coming Soon!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences=getSharedPreferences(Constants.DEFAULT_PREFS_FILE,MODE_PRIVATE);
        /*SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("registered",false);
        editor.apply();*/
        boolean isLoggedIn = sharedPreferences.getBoolean("loggedin", false);
        if (!isLoggedIn){
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }else {
            Fragment fragment=new NasaMainFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment,null).commit();
        }



    }



    private void checkForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT>=23){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==23){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                try {
                    MyApplication.createDirectory();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Couldn't create app folders,is your sd card read only?",Toast.LENGTH_LONG).show();
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.DEFAULT_PREFS_FILE,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("loggedin",false);
        editor.apply();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences sharedPreferences=getSharedPreferences(Constants.DEFAULT_PREFS_FILE,0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("loggedin",false);
            editor.apply();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        if (id == R.id.nav_github) {
            // Handle the camera action
            fragment=new GitHubFragment();
        } else if (id == R.id.nav_nasa) {
            fragment=new NasaMainFragment();
        } else if (id == R.id.nav_stackoverflow) {
            fragment=new MusicLibraryFragment();
        } else if (id == R.id.nasa_saved_images) {
                fragment=new SavedFragment();
        } else if (id == R.id.nasa_fav_images) {
            Bundle bundle=new Bundle();
            bundle.putBoolean("isFavFragment",true);
            fragment = new FavoritesFragment();
            fragment.setArguments(bundle);
                fragment=new FavoritesFragment();
        }

        if (fragment!=null){
            mRelativeLayout.setVisibility(View.GONE);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
