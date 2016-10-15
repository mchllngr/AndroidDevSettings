package de.mchllngr.devsettings.util.debug;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import java.util.Arrays;

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

    /**
     * {@link AppCompatActivity} used for initialising.
     */
    private final AppCompatActivity activity;
    /**
     * {@link String} used for initialising the NightMode-{@link ActionsModule}.
     */
    @BindString(R.string.debug_night_mode_select)
    String debugNightModeSelect;
    /**
     * {@link String} used for initialising the NightMode-{@link ActionsModule}.
     */
    @BindString(R.string.debug_night_mode_yes)
    String debugNightModeYes;
    /**
     * {@link String} used for initialising the NightMode-{@link ActionsModule}.
     */
    @BindString(R.string.debug_night_mode_no)
    String debugNightModeNo;
    /**
     * {@link String} used for initialising the NightMode-{@link ActionsModule}.
     */
    @BindString(R.string.debug_night_mode_auto)
    String debugNightModeAuto;
    /**
     * {@link String} used for initialising the NightMode-{@link ActionsModule}.
     */
    @BindString(R.string.debug_night_mode_follow_system)
    String debugNightModeFollowSystem;
    /**
     * {@link DebugDrawer} reference to use.
     */
    private DebugDrawer debugDrawer;

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
    public void initDebugDrawer() {
        debugDrawer = new DebugDrawer.Builder(activity)
                .modules(
                        new ActionsModule(getNightModeActionsModule()),
                        new NetworkModule(activity),
                        new ScalpelModule(activity),
                        new FpsModule(Takt.stock(activity.getApplication())),
                        new BuildModule(activity),
                        new DeviceModule(activity),
                        new SettingsModule(activity)
                ).build();
    }

    /**
     * Returns the {@link ActionsModule} for selecting the
     * {@link android.support.v7.app.AppCompatDelegate.NightMode}
     *
     * @return {@link ActionsModule} for selecting the
     * {@link android.support.v7.app.AppCompatDelegate.NightMode}
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
                new SpinnerAction.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(@NonNull String value) {
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
                }
        );
    }

    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    public void onStart() {
        debugDrawer.onStart();
    }

    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    public void onResume() {
        debugDrawer.onResume();
    }

    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    public void onPause() {
        debugDrawer.onPause();
    }


    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    public void onStop() {
        debugDrawer.onStop();
    }
}
