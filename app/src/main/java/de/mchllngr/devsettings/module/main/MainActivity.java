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

/**
 * {@link android.app.Activity} for setting the DevSettings.
 * <p>
 * Note that this requires a system level permission, so consumers <b>must</b> run this
 * <code>adb</code> command to use.
 * <p>
 * <code>adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS</code>
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
public class MainActivity extends BaseActivity {

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

    @Override
    protected void onResume() {
        super.onResume();

        setDevSettingsEnabled(DevSettingsUtil.getDevSettingsEnabled(this));
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
