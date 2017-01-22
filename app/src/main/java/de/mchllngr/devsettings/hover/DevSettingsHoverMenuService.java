package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.content.Intent;

import io.mattcarroll.hover.HoverMenuAdapter;
import io.mattcarroll.hover.defaulthovermenu.window.HoverMenuService;

/**
 * {@link HoverMenuService} for showing the floating menu.
 */
public class DevSettingsHoverMenuService extends HoverMenuService {

    private static Intent intent;

    public static void showFloatingMenu(Context context) {
        context.startService(intent = new Intent(context, DevSettingsHoverMenuService.class));
    }

    public static void hideFloatingMenu(Context context) {
        if (intent != null)
            context.stopService(intent);
        else
            context.stopService(new Intent(context, DevSettingsHoverMenuService.class));
    }

    @Override
    protected HoverMenuAdapter createHoverMenuAdapter() {
        DevSettingsHoverMenuAdapter adapter = new DevSettingsHoverMenuAdapter(getContextForHoverMenu());

        return adapter;
    }
}
