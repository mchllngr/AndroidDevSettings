package de.mchllngr.devsettings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import de.mchllngr.devsettings.databinding.ActivityMainBinding
import de.mchllngr.devsettings.extension.flow.launchAndCollectIn
import de.mchllngr.devsettings.servicelocator.ServiceLocator

class MainActivity : AppCompatActivity() {

    private val devSettingsService by ServiceLocator::devSettingsService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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

    private fun setDevSettingsEnabled(enabled: Boolean) {
        binding.devSettings.setColorFilter(
            ContextCompat.getColor(
                this,
                if (enabled) R.color.colorPrimary else R.color.darker_gray
            )
        )
    }
}
