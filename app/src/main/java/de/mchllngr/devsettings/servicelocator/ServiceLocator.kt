package de.mchllngr.devsettings.servicelocator

import android.content.Context
import androidx.work.WorkManager
import de.mchllngr.devsettings.service.devsettings.DevSettingsService

/**
 * Created by michael.langer on 19.10.23
 */
object ServiceLocator {

    lateinit var applicationContext: Context

    val devSettingsService by lazy { DevSettingsService() }

    val workManager by lazy { WorkManager.getInstance(applicationContext) }
}
