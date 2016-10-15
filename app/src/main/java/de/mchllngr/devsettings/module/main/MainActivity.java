package de.mchllngr.devsettings.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

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
     * {@link SwitchCompat} for setting the DevSettings.
     */
    @BindView(R.id.dev_settings)
    SwitchCompat devSettingSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        devSettingSwitch.setOnCheckedChangeListener((view, checked) -> DevSettingsUtil.setDevSettings(this, checked));
    }

    @Override
    protected void onResume() {
        super.onResume();

        devSettingSwitch.setChecked(DevSettingsUtil.getDevSettingsEnabled(this));
    }
}
