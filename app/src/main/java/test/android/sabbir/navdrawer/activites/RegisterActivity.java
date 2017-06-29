package test.android.sabbir.navdrawer.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.MainActivity;
import test.android.sabbir.navdrawer.R;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;


    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_or_login);
        ButterKnife.bind(this);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mProgressDialog=new ProgressDialog(this);

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
}
