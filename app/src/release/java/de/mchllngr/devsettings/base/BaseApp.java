package de.mchllngr.devsettings.base;

import android.app.Application;

import de.mchllngr.devsettings.util.timber.ReleaseTree;
import timber.log.Timber;

/**
 * Base-class used for initialising {@link Timber}.
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
    }

    /**
     * Initialises {@link Timber} with release configuration
     */
    private void initTimber() {
        Timber.plant(new ReleaseTree());
    }
}
