package de.mchllngr.devsettings.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import de.mchllngr.devsettings.util.debug.DebugDrawerHelper;
import io.palaima.debugdrawer.DebugDrawer;

/**
 * BaseDebug-class used for initialization of the {@link DebugDrawer}.
 */
public abstract class DebugBaseActivity extends AppCompatActivity {

    private DebugDrawer debugDrawer;

    /**
     * Overrides {@link AppCompatActivity#setContentView(int)} to
     * allow setting the {@link DebugDrawer} when called.
     *
     * @param layoutResID {@link LayoutRes} used for setting the ContentView
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setDebugDrawer();
    }

    /**
     * Overrides {@link AppCompatActivity#setContentView(View)} to
     * allow setting the {@link DebugDrawer} when called.
     *
     * @param view {@link View} used for setting the ContentView
     */
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setDebugDrawer();
    }

    /**
     * Overrides {@link AppCompatActivity#setContentView(View, ViewGroup.LayoutParams)}
     * to allow setting the {@link DebugDrawer} when called.
     *
     * @param view   {@link View} used for setting the ContentView
     * @param params {@link ViewGroup.LayoutParams} used for setting the ContentView
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setDebugDrawer();
    }

    /**
     * Initialises the {@link DebugDrawer} and sets it.
     */
    private void setDebugDrawer() {
        debugDrawer = new DebugDrawerHelper(this).createDebugDrawer();
    }

    @Override
    public void onBackPressed() {
        if (debugDrawer != null && debugDrawer.isDrawerOpen()) {
            debugDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }
}
