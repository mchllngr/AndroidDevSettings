package de.mchllngr.devsettings.module.starthover;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.mchllngr.devsettings.hover.DevSettingsHoverMenuService;

/**
 * Transparent {@link android.app.Activity}, which will be called when {@link android.content.IntentFilter}
 * {@link android.service.quicksettings.TileService#ACTION_QS_TILE_PREFERENCES} is received.
 * <p>
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
