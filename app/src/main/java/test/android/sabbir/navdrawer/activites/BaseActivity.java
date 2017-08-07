package test.android.sabbir.navdrawer.activites;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by sabbir on 7/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void start(Class<? extends BaseActivity> activityClass){
        startActivity(new Intent(this,activityClass));
    }
}
