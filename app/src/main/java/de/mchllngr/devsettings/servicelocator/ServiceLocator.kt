package de.mchllngr.devsettings.servicelocator

import de.mchllngr.devsettings.service.devsettings.DevSettingsService

/**
 * Created by michael.langer on 19.10.23
 */
object ServiceLocator {

    val devSettingsService by lazy { DevSettingsService() }
}
