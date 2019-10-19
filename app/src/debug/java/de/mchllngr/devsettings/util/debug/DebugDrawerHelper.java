package de.mchllngr.devsettings.util.debug;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import butterknife.BindString;
import butterknife.ButterKnife;
import de.mchllngr.devsettings.R;
import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.actions.ActionsModule;
import io.palaima.debugdrawer.actions.SpinnerAction;
import io.palaima.debugdrawer.commons.BuildModule;
import io.palaima.debugdrawer.commons.DeviceModule;
import io.palaima.debugdrawer.commons.NetworkModule;
import io.palaima.debugdrawer.commons.SettingsModule;
import io.palaima.debugdrawer.fps.FpsModule;
import io.palaima.debugdrawer.scalpel.ScalpelModule;
import jp.wasabeef.takt.Takt;

/**
 * Helper-class for easier use with {@link DebugDrawer}.
 */
public class DebugDrawerHelper {

    private final AppCompatActivity activity;

    @BindString(R.string.debug_night_mode_select) String debugNightModeSelect;
    @BindString(R.string.debug_night_mode_yes) String debugNightModeYes;
    @BindString(R.string.debug_night_mode_no) String debugNightModeNo;
    @BindString(R.string.debug_night_mode_auto) String debugNightModeAuto;
    @BindString(R.string.debug_night_mode_follow_system) String debugNightModeFollowSystem;

    /**
     * Constructor with {@link AppCompatActivity} used for initialising.
     *
     * @param activity {@link AppCompatActivity} used for initialising
     */
    public DebugDrawerHelper(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
    }

    /**
     * Initialises the {@link DebugDrawer}.
     */
    public DebugDrawer createDebugDrawer() {
        return new DebugDrawer.Builder(activity)
                .modules(
                        new ActionsModule(getNightModeActionsModule()),
                        new NetworkModule(),
                        new ScalpelModule(activity),
                        new FpsModule(Takt.stock(activity.getApplication())),
                        new BuildModule(),
                        new DeviceModule(),
                        new SettingsModule()
                ).build();
    }

    /**
     * Returns the {@link ActionsModule} for selecting the{@link AppCompatDelegate.NightMode}
     *
     * @return {@link ActionsModule} for selecting the {@link AppCompatDelegate.NightMode}
     */
    private SpinnerAction getNightModeActionsModule() {
        return new SpinnerAction<>(
                Arrays.asList(
                        debugNightModeSelect,
                        debugNightModeYes,
                        debugNightModeNo,
                        debugNightModeAuto,
                        debugNightModeFollowSystem
                ),
                value -> {
                    int selectedMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

                    if (value.equals(debugNightModeYes))
                        selectedMode = AppCompatDelegate.MODE_NIGHT_YES;
                    else if (value.equals(debugNightModeNo))
                        selectedMode = AppCompatDelegate.MODE_NIGHT_NO;
                    else if (value.equals(debugNightModeAuto))
                        selectedMode = AppCompatDelegate.MODE_NIGHT_AUTO;

                    activity.getDelegate().setLocalNightMode(selectedMode);
                    activity.recreate();
                }
        );
    }
}
