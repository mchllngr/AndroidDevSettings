package de.mchllngr.devsettings.work.disable

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.mchllngr.devsettings.servicelocator.ServiceLocator

class DisableDevSettingsWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val devSettingsService by ServiceLocator::devSettingsService

    override fun doWork(): Result {
        devSettingsService.setEnabled(false)

        return Result.success()
    }

    companion object {

        const val WORK_NAME = "de.mchllngr.devsettings.work.disable.DisableDevSettingsWorker"
    }
}
