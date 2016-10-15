package de.mchllngr.devsettings.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Base-class used for debug initializations.
 *
 * @author Michael Langer (<a href="https://github.com/mchllngr" target="_blank">GitHub</a>)
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        initTimber();
    }

    /**
     * Initialises {@link Timber} with debug configuration
     */
    private void initTimber() {
        Timber.plant(new Timber.DebugTree() {
            // Add the line number to the tag
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + '#' + element.getLineNumber();
            }
        });
    }
}
