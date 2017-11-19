package test.android.sabbir.navdrawer.activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.MainActivity;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.utilites.GPSTracker;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.imageView4)
    ImageView imageView;


    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    private GPSTracker gps;
    private String mLatitude;
    private String mLongitude;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_or_login);
        ButterKnife.bind(this);

        gps=new GPSTracker(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mProgressDialog=new ProgressDialog(this);

        getLocation();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Logging in...Please Wait");
                handleLoginClick();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Registering...Please Wait");
                handleRegisterClick();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hiddenCallToMainActivity();
                return true;
            }
        });
    }

    private void hiddenCallToMainActivity() {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("loggedin",true);
        editor.apply();
        callMainActivity();
    }

    private void handleRegisterClick() {
        if(TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please provide email/password",Toast.LENGTH_SHORT).show();
        }else {
            mProgressDialog.show();
            String email=etEmail.getText().toString().trim();
            String password=etPassword.getText().toString().trim();
            mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                SharedPreferences sharedPreferences=getSharedPreferences(Constants.DEFAULT_PREFS_FILE,0);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("loggedin",true);
                                editor.apply();
                                mProgressDialog.dismiss();
                                callMainActivity();
                            }else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }) ;
        }

    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void callMainActivity() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleLoginClick() {
        if(TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please provide email/password",Toast.LENGTH_SHORT).show();
        }else {
            mProgressDialog.show();
            String email=etEmail.getText().toString().trim();
            String password=etPassword.getText().toString().trim();

            mFirebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                SharedPreferences sharedPreferences=getSharedPreferences(Constants.DEFAULT_PREFS_FILE,0);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("loggedin",true);
                                editor.apply();
                                mProgressDialog.dismiss();
                                callMainActivity();
                            }else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Email/Password Mismatch,try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    private void getLocation() {
        //gps=new GPSTracker(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(gps.canGetLocation()){
                    mLatitude = Double.toString(gps.getLatitude());
                    mLongitude = Double.toString(gps.getLongitude());

                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("lat",mLatitude);
                    editor.putString("lan",mLongitude);
                    editor.apply();

                    // \n is for new line
                    Log.i("LocationData ",mLatitude+","+mLongitude);
                }else{
                    showSettingsAlert();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog=builder.create();
        dialog.show();
       // pDialog.dismiss();
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("GPS is Off");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                //finish();
                recreate();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog dialog=alertDialog.create();

        dialog.show();
    }

    private class CityName{
        Double lat,lon;
        CityName(String lat,String lon){
            this.lat=Double.valueOf(lat);
            this.lon=Double.valueOf(lon);
        }

        public String getCityName(){
            String cityName="";
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses=geocoder.getFromLocation(lat,lon,1);
                cityName=addresses.get(0).getAddressLine(0);
                String statename=addresses.get(0).getAddressLine(1);
                String countryName=addresses.get(0).getAddressLine(2);
                //  Log.i("GeoCoder",cityName+statename+countryName);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return cityName;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        gps.stopUsingGPS();
    }
}
