package de.mchllngr.devsettings.service;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import de.mchllngr.devsettings.util.DevSettingsUtil;

/**
 * A {@link TileService} for setting the DevSettings.
 * <p>
 * Note that this requires a system level permission, so consumers <b>must</b> run this
 * <code>adb</code> command to use.
 * <p>
 * <code>adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS</code>
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
@TargetApi(Build.VERSION_CODES.N)
public class DevSettingsTileService extends TileService {

    public DevSettingsTileService() {
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTileState(DevSettingsUtil.getDevSettingsEnabled(getApplicationContext())
                ? Tile.STATE_ACTIVE
                : Tile.STATE_INACTIVE);
    }

    @Override
    public void onClick() {
        super.onClick();

        unlockAndRun(() -> {
            Tile tile = getQsTile();
            switch (tile.getState()) {
                case Tile.STATE_ACTIVE:
                    if (DevSettingsUtil.setDevSettings(getApplicationContext(), false))
                        updateTileState(Tile.STATE_INACTIVE);
                    break;
                case Tile.STATE_INACTIVE:
                    if (DevSettingsUtil.setDevSettings(getApplicationContext(), true))
                        updateTileState(Tile.STATE_ACTIVE);
                    break;
            }
        });
    }

    /**
     * Updates the state of the shown {@link Tile}.
     */
    private void updateTileState(int state) {
        Tile tile = getQsTile();
        if (tile != null) {
            tile.setState(state);

            Icon icon = tile.getIcon();
            switch (state) {
                case Tile.STATE_ACTIVE:
                    icon.setTint(Color.WHITE);
                    break;
                case Tile.STATE_INACTIVE:
                case Tile.STATE_UNAVAILABLE:
                default:
                    icon.setTint(Color.GRAY);
                    break;
            }

            tile.updateTile();
        }
    }
}