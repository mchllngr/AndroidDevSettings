package de.mchllngr.devsettings.util;

import android.content.ContentResolver;
import android.content.Context;
import android.os.BatteryManager;
import android.provider.Settings;
import android.widget.Toast;

import timber.log.Timber;

/**
 * Util-Class for enabling and disabling the DevSettings.
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
public class DevSettingsUtil {

    /**
     * Enables or disables the DevSettings.
     */
    // TODO return boolean to check if settings were set
    public static void setDevSettings(final Context context, boolean enabled) {
        try {
            if (enabled)
                enableDevSettings(context);
            else
                disableDevSettings(context);
        } catch (SecurityException e) {
            String errorMsg = "Could not set the DevSettings! Maybe the system permission is missing.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(e, errorMsg);
        }
    }

    /**
     * Returns true if the DevSettings are enabled.
     */
    public static boolean getDevSettingsEnabled(final Context context) {
        try {
            return getSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED) == 1F;
        } catch (Settings.SettingNotFoundException e) {
            String errorMsg = "Could not get the DevSettings! Maybe the system permission is missing.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(e, errorMsg);
            return false;
        }
    }

    /**
     * Enables the DevSettings.
     */
    private static void enableDevSettings(Context context) throws SecurityException {
        /**
         * TODO not found yet
         * - dont keep activities
         * - strict mode (add ?)
         */

        ContentResolver cr = context.getContentResolver();

        putSetting(cr, Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                BatteryManager.BATTERY_PLUGGED_AC |
                        BatteryManager.BATTERY_PLUGGED_USB |
                        BatteryManager.BATTERY_PLUGGED_WIRELESS);
        putSetting(cr, Settings.Global.ADB_ENABLED, 1F);

        // TODO put animations in an extra tile/setting (or show picker ?)
        putSetting(cr, Settings.Global.WINDOW_ANIMATION_SCALE, 0F);
        putSetting(cr, Settings.Global.TRANSITION_ANIMATION_SCALE, 0F);
        putSetting(cr, Settings.Global.ANIMATOR_DURATION_SCALE, 0F);
    }

    /**
     * Disables the DevSettings.
     */
    private static void disableDevSettings(Context context) throws SecurityException {
        ContentResolver cr = context.getContentResolver();

        putSetting(cr, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, 0F);
        putSetting(cr, Settings.Global.ADB_ENABLED, 0F);
        putSetting(cr, Settings.Global.WINDOW_ANIMATION_SCALE, 1F);
        putSetting(cr, Settings.Global.TRANSITION_ANIMATION_SCALE, 1F);
        putSetting(cr, Settings.Global.ANIMATOR_DURATION_SCALE, 1F);
    }

    /**
     * Updates a global setting.
     */
    private static void putSetting(final ContentResolver contentResolver, String settingsName, float value) throws SecurityException {
        Settings.Global.putFloat(contentResolver, settingsName, value);
    }

    /**
     * Gets the value of a global setting.
     */
    private static float getSetting(final ContentResolver contentResolver, String settingsName) throws Settings.SettingNotFoundException {
        return Settings.Global.getFloat(contentResolver, settingsName);
    }
}
