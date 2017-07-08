package de.mchllngr.devsettings.module.startHover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.mchllngr.devsettings.hover.DevSettingsHoverMenuService;

/**
 * Transparent {@link android.app.Activity}, which will be called when {@link android.content.IntentFilter}
 * {@link android.service.quicksettings.TileService#ACTION_QS_TILE_PREFERENCES} is received.
 *
 * Will show the hover menu and closes itself.
 */
public class StartHoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DevSettingsHoverMenuService.showHoverMenu(this);
        finish();
    }
}
