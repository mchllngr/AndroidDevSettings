package de.mchllngr.devsettings.service.devsettings

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import de.mchllngr.devsettings.servicelocator.ServiceLocator
import de.mchllngr.devsettings.work.disable.DisableDevSettingsWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit

/**
 * Class for enabling, disabling, getting and setting the DevSettings.
 *
 * Note that this requires a system level permission, so consumers **must** run this `adb` command to use.
 *
 * `adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`
 */
class DevSettingsService : DefaultLifecycleObserver {

    private val applicationContext by ServiceLocator::applicationContext
    private val applicationContentReceiver: ContentResolver by applicationContext::contentResolver
    private val workManager by ServiceLocator::workManager

    private val _enabled = MutableStateFlow(false)
    val enabled: StateFlow<Boolean> = _enabled

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        _enabled.value = isEnabled()
    }

    /**
     * Enables or disables the DevSettings.
     *
     * Returns false if it fails e.g. when the system permission is not granted.
     */
    fun setEnabled(
        enabled: Boolean,
        scheduleAutomaticDisable: Boolean = true
    ): Boolean {
        try {
            if (enabled) {
                setDefaultEnabledSettings()
                if (scheduleAutomaticDisable) scheduleAutomaticDisable()
                _enabled.value = true
            } else {
                setDefaultDisabledSettings()
                cancelAutomaticDisable()
                _enabled.value = false
            }
            return true
        } catch (e: SecurityException) {
            val errorMsg = "Could not set the DevSettings! Maybe the system permission is missing."
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            Log.e(KEY_LOG, errorMsg, e)
        }
        return false
    }

    /**
     * Returns true if DeveloperSettings and ADB are enabled.
     *
     * Returns false, if DeveloperSettings or ADB is disabled or it fails e.g. when the setting is not found.
     */
    fun isEnabled(): Boolean {
        try {
            return isDeveloperSettingsEnabled() && isAdbEnabled()
        } catch (e: SettingNotFoundException) {
            val errorMsg = "Could not get the DevSettings! Maybe the system permission is missing."
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            Log.e(KEY_LOG, errorMsg, e)
        }
        return false
    }

    @Throws(SecurityException::class)
    private fun setDefaultEnabledSettings() {
        setDeveloperSettingsEnabled(true)
        setAdbEnabled(true)
        setStayAwakeEnabled(true)
        setDontKeepActivitiesEnabled(true)
    }

    @Throws(SecurityException::class)
    private fun setDefaultDisabledSettings() {
        setAdbEnabled(false)
        setStayAwakeEnabled(false)
        setAnimationsScale(1f)
        setDontKeepActivitiesEnabled(false)
    }

    fun openDeveloperOptions(activityContext: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activityContext.startActivity(intent)
    }

    @Throws(SettingNotFoundException::class)
    private fun isDeveloperSettingsEnabled() = getSetting(Settings.Global.DEVELOPMENT_SETTINGS_ENABLED) == 1f

    @Throws(SecurityException::class)
    private fun setDeveloperSettingsEnabled(enabled: Boolean) {
        putSetting(Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, if (enabled) 1 else 0)
    }

    @Throws(SettingNotFoundException::class)
    private fun isAdbEnabled() = getSetting(Settings.Global.ADB_ENABLED) == 1f

    @Throws(SecurityException::class)
    private fun setAdbEnabled(enabled: Boolean) {
        putSetting(Settings.Global.ADB_ENABLED, if (enabled) 1 else 0)
    }

    @Throws(SettingNotFoundException::class)
    private fun isStayAwakeEnabled(): Boolean {
        val enabledValue = BatteryManager.BATTERY_PLUGGED_AC or BatteryManager.BATTERY_PLUGGED_USB or BatteryManager.BATTERY_PLUGGED_WIRELESS
        return getSetting(Settings.Global.STAY_ON_WHILE_PLUGGED_IN) == enabledValue.toFloat()
    }

    @Throws(SecurityException::class)
    private fun setStayAwakeEnabled(enabled: Boolean) {
        val value = if (enabled) {
            BatteryManager.BATTERY_PLUGGED_AC or BatteryManager.BATTERY_PLUGGED_USB or BatteryManager.BATTERY_PLUGGED_WIRELESS
        } else {
            0
        }
        putSetting(Settings.Global.STAY_ON_WHILE_PLUGGED_IN, value)
    }

    @Throws(SettingNotFoundException::class)
    private fun getAnimationsScale() = getSetting(Settings.Global.ANIMATOR_DURATION_SCALE)

    @Throws(SecurityException::class)
    private fun setAnimationsScale(scale: Float) {
        putSetting(Settings.Global.WINDOW_ANIMATION_SCALE, scale)
        putSetting(Settings.Global.TRANSITION_ANIMATION_SCALE, scale)
        putSetting(Settings.Global.ANIMATOR_DURATION_SCALE, scale)
    }

    @Throws(SettingNotFoundException::class)
    private fun isDontKeepActivitiesEnabled() = getSetting(Settings.Global.ALWAYS_FINISH_ACTIVITIES) == 1f

    @Throws(SecurityException::class)
    private fun setDontKeepActivitiesEnabled(enabled: Boolean) {
        putSetting(Settings.Global.ALWAYS_FINISH_ACTIVITIES, if (enabled) 1 else 0)
    }

    @Throws(SecurityException::class)
    private fun putSetting(
        settingsName: String,
        value: Int
    ) {
        Settings.Global.putInt(applicationContentReceiver, settingsName, value)
    }

    @Throws(SecurityException::class)
    private fun putSetting(
        settingsName: String,
        value: Float
    ) {
        Settings.Global.putFloat(applicationContentReceiver, settingsName, value)
    }

    @Throws(SettingNotFoundException::class)
    private fun getSetting(settingsName: String) = Settings.Global.getFloat(applicationContentReceiver, settingsName)

    private fun scheduleAutomaticDisable() {
        val request = OneTimeWorkRequestBuilder<DisableDevSettingsWorker>()
            .setInitialDelay(HOURS_BEFORE_AUTOMATIC_DISABLE, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresDeviceIdle(true)
                    .build()
            )
            .build()

        workManager.enqueueUniqueWork(
            DisableDevSettingsWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun cancelAutomaticDisable() {
        workManager.cancelUniqueWork(DisableDevSettingsWorker.WORK_NAME)
    }

    companion object {

        private const val KEY_LOG = "DevSettingsService"
        private const val HOURS_BEFORE_AUTOMATIC_DISABLE = 12L
    }
}
