package com.cyanogenmod.settings.device;

import java.io.IOException;
import android.content.Context;
import android.util.AttributeSet;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

public class BLNTimeout extends ListPreference implements OnPreferenceChangeListener {

   public BLNTimeout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPreferenceChangeListener(this);
    }

    private static final String FILE = "/sys/class/misc/backlightnotification/notification_timeout";
    
	public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }
    
    /**
     * Restore BLNTimeout setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) 
	{
        if (!isSupported()) 
		{
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Utils.writeValue(FILE, sharedPrefs.getString(DeviceSettings.KEY_BLN_TIMEOUT, "60000"));
    }

 	//@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) 
	{
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }

    

   

}

