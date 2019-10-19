package de.mchllngr.devsettings.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.mchllngr.devsettings.hover.DevSettingsHoverMenuService;
import timber.log.Timber;

/**
 * Class for enabling, disabling, getting and setting the DevSettings.
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
    public static boolean setEnabled(@Nullable final Context context, boolean enabled) {
        if (context == null) return false;
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
    public static boolean isEnabled(@Nullable final Context context) {
        try {
            return isDeveloperSettingsEnabled(context) && isAdbEnabled(context);
        } catch (Settings.SettingNotFoundException e) {
            String errorMsg = "Could not get the DevSettings! Maybe the system permission is missing.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(e, errorMsg);
            return false;
        }
    }

    private static void setDefaultEnabledSettings(@Nullable final Context context) throws SecurityException {
        setDeveloperSettingsEnabled(context, true);
        setAdbEnabled(context, true);
        setStayAwakeEnabled(context, true);
        setDontKeepActivitiesEnabled(context, true);
    }

    private static void setDefaultDisabledSettings(@Nullable final Context context) throws SecurityException {
        setAdbEnabled(context, false);
        setStayAwakeEnabled(context, false);
        setAnimationsScale(context, 1F);
        setDontKeepActivitiesEnabled(context, false);
    }

    private static void putSetting(@NonNull final ContentResolver contentResolver, @NonNull String settingsName, int value) throws SecurityException {
        Settings.Global.putInt(contentResolver, settingsName, value);
    }

    private static void putSetting(@NonNull final ContentResolver contentResolver, @NonNull String settingsName, float value) throws SecurityException {
        Settings.Global.putFloat(contentResolver, settingsName, value);
    }

    private static float getSetting(@NonNull final ContentResolver contentResolver, @NonNull String settingsName) throws Settings.SettingNotFoundException {
        return Settings.Global.getFloat(contentResolver, settingsName);
    }

    /**
     * Opens the developer options menu.
     */
    public static void openDeveloperOptions(@Nullable final Context context) {
        if (context == null) return;
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean isDeveloperSettingsEnabled(@Nullable final Context context) throws Settings.SettingNotFoundException {
        return context != null && getSetting(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED) == 1F;
    }

    public static void setDeveloperSettingsEnabled(@Nullable final Context context, boolean enabled) throws SecurityException {
        if (context == null) return;
        putSetting(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, enabled ? 1 : 0);
    }

    public static boolean isAdbEnabled(@Nullable final Context context) throws Settings.SettingNotFoundException {
        return context != null && getSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED) == 1F;
    }

    public static void setAdbEnabled(@Nullable final Context context, boolean enabled) throws SecurityException {
        if (context == null) return;
        putSetting(context.getContentResolver(), Settings.Global.ADB_ENABLED, enabled ? 1 : 0);
    }

    public static boolean isStayAwakeEnabled(@Nullable final Context context) throws Settings.SettingNotFoundException {
        int enabledValue = BatteryManager.BATTERY_PLUGGED_AC | BatteryManager.BATTERY_PLUGGED_USB | BatteryManager.BATTERY_PLUGGED_WIRELESS;
        return context != null && getSetting(context.getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN) == enabledValue;
    }

    public static void setStayAwakeEnabled(@Nullable final Context context, boolean enabled) throws SecurityException {
        if (context == null) return;

        int value = 0;
        if (enabled)
            value = BatteryManager.BATTERY_PLUGGED_AC | BatteryManager.BATTERY_PLUGGED_USB | BatteryManager.BATTERY_PLUGGED_WIRELESS;

        putSetting(context.getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, value);
    }

    public static float getAnimationsScale(@Nullable final Context context) throws Settings.SettingNotFoundException {
        if (context == null) return 1F;
        return getSetting(context.getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE);
    }

    public static void setAnimationsScale(@Nullable final Context context, float scale) throws SecurityException {
        if (context == null) return;
        ContentResolver cr = context.getContentResolver();
        putSetting(cr, Settings.Global.WINDOW_ANIMATION_SCALE, scale);
        putSetting(cr, Settings.Global.TRANSITION_ANIMATION_SCALE, scale);
        putSetting(cr, Settings.Global.ANIMATOR_DURATION_SCALE, scale);
    }

    public static boolean isDontKeepActivitiesEnabled(@Nullable final Context context) throws Settings.SettingNotFoundException {
        return context != null && getSetting(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES) == 1F;
    }

    public static void setDontKeepActivitiesEnabled(@Nullable final Context context, boolean enabled) throws SecurityException {
        if (context == null) return;
        putSetting(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, enabled ? 1 : 0);
    }
}
