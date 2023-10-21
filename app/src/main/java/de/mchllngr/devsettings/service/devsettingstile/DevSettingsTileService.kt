package de.mchllngr.devsettings.service.devsettingstile

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import de.mchllngr.devsettings.R
import de.mchllngr.devsettings.servicelocator.ServiceLocator

/**
 * A [TileService] for setting the DevSettings.
 *
 * Note that this requires a system level permission, so consumers **must** run this `adb` command to use.
 *
 * `adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`
 */
class DevSettingsTileService : TileService() {

    private val devSettingsService by ServiceLocator::devSettingsService

    override fun onStartListening() {
        super.onStartListening()
        updateTileState(devSettingsService.isEnabled())
    }

    override fun onClick() {
        super.onClick()

        unlockAndRun {
            when (qsTile?.state) {
                Tile.STATE_ACTIVE -> if (devSettingsService.setEnabled(false)) updateTileState(false)
                Tile.STATE_INACTIVE -> if (devSettingsService.setEnabled(true)) updateTileState(true)
            }
        }
    }

    private fun updateTileState(enabled: Boolean) {
        val tile = qsTile ?: return

        tile.state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = getString(if (enabled) R.string.dev_settings_tile_on else R.string.dev_settings_tile_off)
        }

        tile.contentDescription = tile.label

        tile.updateTile()
    }
}
