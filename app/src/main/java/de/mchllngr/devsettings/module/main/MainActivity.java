package de.mchllngr.devsettings.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.mchllngr.devsettings.R;
import de.mchllngr.devsettings.base.BaseActivity;
import de.mchllngr.devsettings.util.DevSettingsUtil;

/**
 * {@link android.app.Activity} for setting the DevSettings.
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
            DevSettingsUtil.setDevSettings(this, !devSettingsEnabled);
            setDevSettingsEnabled(!devSettingsEnabled);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setDevSettingsEnabled(DevSettingsUtil.getDevSettingsEnabled(this));
    }

    private void setDevSettingsEnabled(boolean enabled) {
        devSettingsEnabled = enabled;
        devSettings.setColorFilter(ContextCompat.getColor(
                this,
                devSettingsEnabled ? R.color.colorPrimary : android.R.color.darker_gray
        ));
    }
}
