package de.mchllngr.devsettings.ui.developersettings

import android.app.Activity
import android.content.IntentFilter
import android.os.Bundle
import android.service.quicksettings.TileService
import androidx.appcompat.app.AppCompatActivity
import de.mchllngr.devsettings.servicelocator.ServiceLocator

/**
 * An [Activity] that will be called when [IntentFilter] [TileService.ACTION_QS_TILE_PREFERENCES] is received.
 *
 * It will open the developer settings and close itself.
 */
class OpenDeveloperSettingsActivity : AppCompatActivity() {

    private val devSettingsService by ServiceLocator::devSettingsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        devSettingsService.openDeveloperOptions(this)

        finish()
    }
}
