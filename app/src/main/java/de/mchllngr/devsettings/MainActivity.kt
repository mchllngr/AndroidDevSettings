package de.mchllngr.devsettings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import de.mchllngr.devsettings.databinding.ActivityMainBinding
import de.mchllngr.devsettings.extension.flow.launchAndCollectIn
import de.mchllngr.devsettings.servicelocator.ServiceLocator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val devSettingsService = ServiceLocator.devSettingsService
        lifecycle.addObserver(devSettingsService)

        binding.devSettings.setOnClickListener {
            devSettingsService.setEnabled(!devSettingsService.isEnabled())
        }

        devSettingsService.enabled.launchAndCollectIn(this) {
            setDevSettingsEnabled(it)
        }
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
