package de.mchllngr.devsettings.hover;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.mchllngr.devsettings.R;
import de.mchllngr.devsettings.util.DevSettings;
import io.mattcarroll.hover.Content;
import timber.log.Timber;

/**
 * {@link Content} for the hover menu screen showing the DevSettings.
 */
public class DevSettingsHoverScreen implements Content {

    @BindView(R.id.cb_hover_stay_awake) CheckBox cbStayAwake;
    @BindView(R.id.cb_hover_show_tabs) CheckBox cbShowTabs;
    @BindView(R.id.cb_hover_show_surface_updates) CheckBox cbShowSurfaceUpdates;
    @BindView(R.id.cb_hover_show_layout_bounds) CheckBox cbShowLayoutBounds;
    @BindView(R.id.cb_hover_force_rtl_layout_direction) CheckBox cbForceRtlLayoutDirection;
    @BindView(R.id.tv_hover_animation_scale) TextView tvAnimationScale;
    @BindView(R.id.cb_hover_strict_mode_enabled) CheckBox cbStrictModeEnabled;
    @BindView(R.id.cb_hover_profile_gpu_rendering) CheckBox cbProfileGpuRendering;
    @BindView(R.id.cb_hover_dont_keep_activities) CheckBox cbDontKeepActivities;
    @BindView(R.id.cb_hover_force_activities_to_be_resizable) CheckBox cbForceActivitiesToBeResizable;
    @BindView(R.id.tv_hover_open_dev_settings) TextView tvOpenDevSettings;

    private final Context context;
    private View view;

    DevSettingsHoverScreen(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public View getView() {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_hover_screen, null);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public void onShown() {
        try {
            // restore states
            cbStayAwake.setChecked(DevSettings.isStayAwakeEnabled(context));
            setCheckBoxTodo(cbShowTabs);
            setCheckBoxTodo(cbShowSurfaceUpdates);
            setCheckBoxTodo(cbShowLayoutBounds);
            setCheckBoxTodo(cbForceRtlLayoutDirection);
//            tvAnimationScale // TODO ##################################################################################################################### TODO
            setCheckBoxTodo(cbStrictModeEnabled);
            setCheckBoxTodo(cbProfileGpuRendering);
            cbDontKeepActivities.setChecked(DevSettings.isDontKeepActivitiesEnabled(context));
            setCheckBoxTodo(cbForceActivitiesToBeResizable);
        } catch (Settings.SettingNotFoundException e) {
            Timber.e(e, "Error while restoring states.");
        }

        // set listeners
        cbStayAwake.setOnCheckedChangeListener((view, isChecked) -> DevSettings.setStayAwakeEnabled(context, isChecked));
        cbDontKeepActivities.setOnCheckedChangeListener((view, isChecked) -> DevSettings.setDontKeepActivitiesEnabled(context, isChecked));
        tvOpenDevSettings.setOnClickListener(view -> DevSettings.openDeveloperOptions(context));
    }

    @Override
    public void onHidden() { /* no-op */ }

    /**
     * Sets the given checkBox to state 'TODO' because I couldn't find the appropriate settings yet
     */
    @SuppressLint("SetTextI18n")
    private void setCheckBoxTodo(@NonNull CheckBox checkBoxTodo) {
        checkBoxTodo.setEnabled(false);
        String todoSuffix = " (TODO)";
        if (!checkBoxTodo.getText().toString().endsWith(todoSuffix))
            checkBoxTodo.setText(checkBoxTodo.getText() + todoSuffix);
    }
}
