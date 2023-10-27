package de.mchllngr.devsettings.service.devsettings

/**
 * Created by michael.langer on 19.10.23
 */
data class DevSettings(
    val enabled: Boolean,
    val adbEnabled: Boolean,
    val stayAwakeEnabled: Boolean,
    val animationsScale: Float,
    val dontKeepActivitiesEnabled: Boolean,
)
