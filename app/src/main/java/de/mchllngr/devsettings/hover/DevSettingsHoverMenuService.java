package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import de.mchllngr.devsettings.util.DevSettings;
import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;
import timber.log.Timber;

/**
 * {@link HoverMenuService} for showing the floating menu.
 */
public class DevSettingsHoverMenuService extends HoverMenuService {

    private static Intent intent;

    public static void showHoverMenu(Context context) {
        hideHoverMenu(context);
        if (DevSettings.isEnabled(context))
            context.startService(intent = new Intent(context, DevSettingsHoverMenuService.class));
        else {
            String errorMsg = "Can not show hover menu when the DevSettings are disabled.";
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            Timber.e(errorMsg);
        }
    }

    public static void hideHoverMenu(Context context) {
        if (intent != null)
            context.stopService(intent);
        else
            context.stopService(new Intent(context, DevSettingsHoverMenuService.class));
    }

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
    }

    @NonNull
    private HoverMenu createHoverMenu() {
        return new MultiSectionHoverMenu(getApplicationContext());
    }
}
