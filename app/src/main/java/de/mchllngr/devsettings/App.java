package de.mchllngr.devsettings;

import androidx.appcompat.app.AppCompatDelegate;
import de.mchllngr.devsettings.base.BaseApp;

/**
 * {@link App} for the {@link android.app.Application}
 */
public class App extends BaseApp {

    /**
     * Sets the default night mode to follow system.
     */
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
