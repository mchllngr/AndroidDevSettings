package de.mchllngr.devsettings

import android.app.Application
import de.mchllngr.devsettings.servicelocator.ServiceLocator

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.applicationContext = this
    }
}
