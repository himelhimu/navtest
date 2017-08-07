package test.android.sabbir.navdrawer.sip;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import test.android.sabbir.navdrawer.R;

/**
 * Created by sabbir on 7/12/17.
 */

public class SipSettings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Note that none of the preferences are actually defined here.
        // They're all in the XML file res/xml/preferences.xml.
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefernces);
    }
}
