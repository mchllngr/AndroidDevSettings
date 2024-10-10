package de.mchllngr.devsettings.ui.main

import android.app.Activity
import android.app.StatusBarManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import de.mchllngr.devsettings.R
import de.mchllngr.devsettings.databinding.ActivityMainBinding
import de.mchllngr.devsettings.extension.flow.launchAndCollectIn
import de.mchllngr.devsettings.service.devsettingstile.DevSettingsTileService
import de.mchllngr.devsettings.servicelocator.ServiceLocator

/**
 * An [Activity] for setting the DevSettings.
 *
 * Note that this requires a system level permission, so consumers **must** run this `adb` command to use.
 *
 * `adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`
 */
class MainActivity : AppCompatActivity() {

    private val devSettingsService by ServiceLocator::devSettingsService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        lifecycle.addObserver(devSettingsService)

        binding.devSettings.setOnClickListener {
            devSettingsService.setEnabled(!devSettingsService.isEnabled())
        }

        devSettingsService.enabled.launchAndCollectIn(this) {
            setDevSettingsEnabled(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.developer_options) {
            devSettingsService.openDeveloperOptions(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        requestAddDevSettingsTileService()
    }

    private fun setDevSettingsEnabled(enabled: Boolean) {
        binding.devSettings.setColorFilter(
            ContextCompat.getColor(
                this,
                if (enabled) R.color.colorPrimary else R.color.darker_gray
            )
        )
    }

    private fun requestAddDevSettingsTileService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val statusBarManager: StatusBarManager = getSystemService(StatusBarManager::class.java) ?: return

        statusBarManager.requestAddTileService(
            ComponentName(
                this,
                DevSettingsTileService::class.java
            ),
            getString(R.string.dev_settings_tile_label),
            Icon.createWithResource(this, R.drawable.ic_adb_white_24dp),
            { /* do nothing */ },
            { /* do nothing */ }
        )
    }
}
