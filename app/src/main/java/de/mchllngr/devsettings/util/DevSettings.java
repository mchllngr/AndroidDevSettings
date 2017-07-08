package de.mchllngr.devsettings.util;

import android.content.ContentResolver;
import android.content.Context;
import android.os.BatteryManager;
import android.provider.Settings;
import android.widget.Toast;

import de.mchllngr.devsettings.hover.DevSettingsHoverMenuService;
import timber.log.Timber;

/**
 * Util-Class for enabling and disabling the DevSettings.
 * <p>
 * Note that this requires a system level permission, so consumers <b>must</b> run this <code>adb</code> command to use.
 * <p>
 * <code>adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS</code>
 */
@SuppressWarnings("WeakerAccess")
public class DevSettings {

    /**
     * Enables or disables the DevSettings.
     * <p>
     * Returns false if it fails e.g. when the system permission is not granted.
     */
    public static boolean setEnabled(final Context context, boolean enabled) {
        try {
            if (enabled) {
                setDefaultEnabledSettings(context);
                DevSettingsHoverMenuService.showHoverMenu(context);
            } else {
                DevSettingsHoverMenuService.hideHoverMenu(context);
                setDefaultDisabledSettings(context);
            }
            return true;
        } catch (SecurityException e) {
            String errorMsg = "Could not set the DevSettings! Maybe the system permission is missing.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(e, errorMsg);
            return false;
        }
    }

    /**
     * Returns true if DeveloperSettings and ADB are enabled.
     * <p>
     * Returns false, if DeveloperSettings or ADB is disabled or it fails e.g. when the setting is not found.
     */
    public static boolean isEnabled(final Context context) {
        try {
            return getSetting(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED) == 1F
                    && getSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED) == 1F;
        } catch (Settings.SettingNotFoundException e) {
            String errorMsg = "Could not get the DevSettings! Maybe the system permission is missing.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(e, errorMsg);
            return false;
        }
    }

    private static void setDefaultEnabledSettings(final Context context) throws SecurityException {
        setDeveloperSettingsEnabled(context, true);
        setAdbEnabled(context, true);
        setStayOnWhilePluggedIn(context, true);
        setAlwaysFinishActivities(context, true);
    }

    private static void setDefaultDisabledSettings(final Context context) throws SecurityException {
        setAdbEnabled(context, false);
        setStayOnWhilePluggedIn(context, false);
        setAnimationsScale(context, 1F);
        setAlwaysFinishActivities(context, false);
    }

    private static void putSetting(final ContentResolver contentResolver, String settingsName, int value) throws SecurityException {
        Settings.Global.putInt(contentResolver, settingsName, value);
    }

    private static void putSetting(final ContentResolver contentResolver, String settingsName, float value) throws SecurityException {
        Settings.Global.putFloat(contentResolver, settingsName, value);
    }

    private static float getSetting(final ContentResolver contentResolver, String settingsName) throws Settings.SettingNotFoundException {
        return Settings.Global.getFloat(contentResolver, settingsName);
    }

    public static float getDeveloperSettingsEnabled(final Context context) throws Settings.SettingNotFoundException {
        return getSetting(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED);
    }

    public static void setDeveloperSettingsEnabled(final Context context, boolean enabled) throws SecurityException {
        putSetting(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, enabled ? 1 : 0);
    }

    public static float getAdbEnabled(final Context context) throws Settings.SettingNotFoundException {
        return getSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED);
    }

    public static void setAdbEnabled(final Context context, boolean enabled) throws SecurityException {
        putSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED, enabled ? 1 : 0);
    }

    public static float getStayOnWhilePluggedIn(final Context context) throws Settings.SettingNotFoundException {
        return getSetting(context.getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN);
    }

    public static void setStayOnWhilePluggedIn(final Context context, boolean enabled) throws SecurityException {
        int value = 0;
        if (enabled)
            value = BatteryManager.BATTERY_PLUGGED_AC | BatteryManager.BATTERY_PLUGGED_USB | BatteryManager.BATTERY_PLUGGED_WIRELESS;

        putSetting(context.getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, value);
    }

    public static float getAnimationsScale(final Context context) throws Settings.SettingNotFoundException {
        return getSetting(context.getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE);
    }

    public static void setAnimationsScale(final Context context, float value) throws SecurityException {
        ContentResolver cr = context.getContentResolver();
        putSetting(cr, Settings.Global.WINDOW_ANIMATION_SCALE, value);
        putSetting(cr, Settings.Global.TRANSITION_ANIMATION_SCALE, value);
        putSetting(cr, Settings.Global.ANIMATOR_DURATION_SCALE, value);
    }

    public static float getAlwaysFinishActivities(final Context context) throws Settings.SettingNotFoundException {
        return getSetting(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES);
    }

    public static void setAlwaysFinishActivities(final Context context, boolean enabled) throws SecurityException {
        putSetting(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, enabled ? 1 : 0);
    }
}
