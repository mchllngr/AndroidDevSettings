package de.mchllngr.devsettings;

import android.support.v7.app.AppCompatDelegate;

import de.mchllngr.devsettings.base.BaseApp;

/**
 * {@link App} for the {@link android.app.Application}
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
public class App extends BaseApp {

    /**
     * Sets the default night mode to follow system.
     */
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
