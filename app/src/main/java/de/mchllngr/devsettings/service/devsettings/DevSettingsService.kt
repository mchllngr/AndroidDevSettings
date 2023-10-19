package de.mchllngr.devsettings.service.devsettings

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.widget.Toast

/**
 * Class for enabling, disabling, getting and setting the DevSettings.
 *
 * Note that this requires a system level permission, so consumers **must** run this `adb` command to use.
 *
 * `adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`
 */
class DevSettingsService {

    /**
     * Enables or disables the DevSettings.
     *
     * Returns false if it fails e.g. when the system permission is not granted.
     */
    fun setEnabled(
        context: Context,
        enabled: Boolean
    ): Boolean {
        try {
            if (enabled) {
                setDefaultEnabledSettings(context)
//                DevSettingsHoverMenuService.showHoverMenu(context); // TODO move to bubbles?
            } else {
//                DevSettingsHoverMenuService.hideHoverMenu(context); // TODO move to bubbles?
                setDefaultDisabledSettings(context)
            }
            return true
        } catch (e: SecurityException) {
            val errorMsg = "Could not set the DevSettings! Maybe the system permission is missing."
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            Log.e(KEY_LOG, errorMsg, e)
        }
        return false
    }

    /**
     * Returns true if DeveloperSettings and ADB are enabled.
     *
     * Returns false, if DeveloperSettings or ADB is disabled or it fails e.g. when the setting is not found.
     */
    fun isEnabled(context: Context): Boolean {
        try {
            return isDeveloperSettingsEnabled(context) && isAdbEnabled(context)
        } catch (e: SettingNotFoundException) {
            val errorMsg = "Could not get the DevSettings! Maybe the system permission is missing."
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            Log.e(KEY_LOG, errorMsg, e)
        }
        return false
    }

    @Throws(SecurityException::class)
    private fun setDefaultEnabledSettings(context: Context) {
        setDeveloperSettingsEnabled(context, true)
        setAdbEnabled(context, true)
        setStayAwakeEnabled(context, true)
        setDontKeepActivitiesEnabled(context, true)
    }

    @Throws(SecurityException::class)
    private fun setDefaultDisabledSettings(context: Context) {
        setAdbEnabled(context, false)
        setStayAwakeEnabled(context, false)
        setAnimationsScale(context, 1f)
        setDontKeepActivitiesEnabled(context, false)
    }

    @Throws(SecurityException::class)
    private fun putSetting(
        contentResolver: ContentResolver,
        settingsName: String,
        value: Int
    ) {
        Settings.Global.putInt(contentResolver, settingsName, value)
    }

    @Throws(SecurityException::class)
    private fun putSetting(
        contentResolver: ContentResolver,
        settingsName: String,
        value: Float
    ) {
        Settings.Global.putFloat(contentResolver, settingsName, value)
    }

    @Throws(SettingNotFoundException::class)
    private fun getSetting(
        contentResolver: ContentResolver,
        settingsName: String
    ) = Settings.Global.getFloat(contentResolver, settingsName)

    fun openDeveloperOptions(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    @Throws(SettingNotFoundException::class)
    fun isDeveloperSettingsEnabled(context: Context) = getSetting(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED) == 1f

    @Throws(SecurityException::class)
    fun setDeveloperSettingsEnabled(
        context: Context,
        enabled: Boolean
    ) {
        putSetting(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, if (enabled) 1 else 0)
    }

    @Throws(SettingNotFoundException::class)
    fun isAdbEnabled(context: Context) = getSetting(context.contentResolver, Settings.Global.ADB_ENABLED) == 1f

    @Throws(SecurityException::class)
    fun setAdbEnabled(
        context: Context,
        enabled: Boolean
    ) {
        putSetting(context.contentResolver, Settings.Global.ADB_ENABLED, if (enabled) 1 else 0)
    }

    @Throws(SettingNotFoundException::class)
    fun isStayAwakeEnabled(context: Context): Boolean {
        val enabledValue = BatteryManager.BATTERY_PLUGGED_AC or BatteryManager.BATTERY_PLUGGED_USB or BatteryManager.BATTERY_PLUGGED_WIRELESS
        return getSetting(context.contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN) == enabledValue.toFloat()
    }

    @Throws(SecurityException::class)
    fun setStayAwakeEnabled(
        context: Context,
        enabled: Boolean
    ) {
        val value =
            if (enabled) BatteryManager.BATTERY_PLUGGED_AC or BatteryManager.BATTERY_PLUGGED_USB or BatteryManager.BATTERY_PLUGGED_WIRELESS
            else 0
        putSetting(context.contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, value)
    }

    @Throws(SettingNotFoundException::class)
    fun getAnimationsScale(context: Context) = getSetting(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE)

    @Throws(SecurityException::class)
    fun setAnimationsScale(
        context: Context,
        scale: Float
    ) {
        with(context.contentResolver) {
            putSetting(this, Settings.Global.WINDOW_ANIMATION_SCALE, scale)
            putSetting(this, Settings.Global.TRANSITION_ANIMATION_SCALE, scale)
            putSetting(this, Settings.Global.ANIMATOR_DURATION_SCALE, scale)
        }
    }

    @Throws(SettingNotFoundException::class)
    fun isDontKeepActivitiesEnabled(context: Context) = getSetting(context.contentResolver, Settings.Global.ALWAYS_FINISH_ACTIVITIES) == 1f

    @Throws(SecurityException::class)
    fun setDontKeepActivitiesEnabled(
        context: Context,
        enabled: Boolean
    ) {
        putSetting(context.contentResolver, Settings.Global.ALWAYS_FINISH_ACTIVITIES, if (enabled) 1 else 0)
    }

    companion object {

        private const val KEY_LOG = "DevSettings"
    }
}
