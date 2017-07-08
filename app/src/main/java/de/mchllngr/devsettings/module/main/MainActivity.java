package de.mchllngr.devsettings.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.mchllngr.devsettings.R;
import de.mchllngr.devsettings.base.BaseActivity;
import de.mchllngr.devsettings.util.DevSettingsUtil;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * {@link ImageView} for setting the DevSettings.
     */
    @BindView(R.id.dev_settings)
    ImageView devSettings;

    /**
     * Flag for only asking for permission once.
     */
    private boolean permissionsRequested = false;
    /**
     * Saves the current status of the DevSettings.
     */
    private boolean devSettingsEnabled = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        devSettings.setOnClickListener((view) -> {
            if (DevSettingsUtil.setDevSettings(this, !devSettingsEnabled))
                setDevSettingsEnabled(!devSettingsEnabled);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.developer_options:
                openDeveloperOptions();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onResume() {
        super.onResume();

        setDevSettingsEnabled(DevSettingsUtil.getDevSettingsEnabled(this));

        // On Android M and above we need to ask the user for permission to display the Hover
        // menu within the "alert window" layer.  Use OverlayPermission to check for the permission
        // and to request it.
        if (!permissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(this)) {
            @SuppressWarnings("NewApi")
            Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(this);
            startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_HOVER_PERMISSION == requestCode) {
            permissionsRequested = true;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Sets the DevSettings-icon as enabled/disabled.
     */
    private void setDevSettingsEnabled(boolean enabled) {
        devSettingsEnabled = enabled;
        devSettings.setColorFilter(ContextCompat.getColor(
                this,
                devSettingsEnabled ? R.color.colorPrimary : android.R.color.darker_gray
        ));
    }

    /**
     * Opens the developer options menu.
     */
    private void openDeveloperOptions() {
        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
    }
}
