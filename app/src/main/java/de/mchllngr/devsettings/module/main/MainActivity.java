package de.mchllngr.devsettings.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.mchllngr.devsettings.R;
import de.mchllngr.devsettings.base.BaseActivity;
import de.mchllngr.devsettings.hover.DevSettingsHoverMenuService;
import de.mchllngr.devsettings.util.DevSettings;
import io.mattcarroll.hover.overlay.OverlayPermission;

/**
 * {@link android.app.Activity} for setting the DevSettings.
 * <p>
 * Note that this requires a system level permission, so consumers <b>must</b> run this <code>adb</code> command to use.
 * <p>
 * <code>adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS</code>
 */
public class MainActivity extends BaseActivity {

    /**
     * RequestCode for showing permission screen.
     */
    private static final int REQUEST_CODE_HOVER_PERMISSION = 46837;

    /**
     * {@link Toolbar} for this {@link android.app.Activity}.
     */
    @BindView(R.id.toolbar) Toolbar toolbar;
    /**
     * {@link ImageView} for setting the DevSettings.
     */
    @BindView(R.id.dev_settings) ImageView devSettings;

    /**
     * Flag for only asking for permission once.
     */
    private boolean permissionsRequested = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        devSettings.setOnClickListener(view -> {
            boolean devSettingsEnabled = DevSettings.isEnabled(this);
            if (DevSettings.setEnabled(this, !devSettingsEnabled))
                setDevSettingsEnabled(!devSettingsEnabled, false);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.developer_options) {
            DevSettings.openDeveloperOptions(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasOverlayPermission(true))
            setDevSettingsEnabled(DevSettings.isEnabled(this), true);
    }

    private boolean hasOverlayPermission(boolean askForPermission) {
        // On Android M and above we need to ask the user for permission to display the Hover menu within the "alert window" layer.
        // Use OverlayPermission to check for the permission and to request it.
        if (!permissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(this)) {
            if (askForPermission) {
                @SuppressWarnings("NewApi")
                Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(this);
                startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
            }
            return false;
        } else
            return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_HOVER_PERMISSION == requestCode) {
            permissionsRequested = true;
            setDevSettingsEnabled(DevSettings.isEnabled(this), true);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Sets the DevSettings-icon enabled/disabled and shows the hover menu if necessary.
     */
    private void setDevSettingsEnabled(boolean enabled, boolean openHoverMenu) {
        devSettings.setColorFilter(ContextCompat.getColor(
                this,
                enabled ? R.color.colorPrimary : android.R.color.darker_gray
        ));

        if (enabled && openHoverMenu && hasOverlayPermission(false))
            DevSettingsHoverMenuService.showHoverMenu(this);
    }
}
