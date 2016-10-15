package de.mchllngr.devsettings.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import de.mchllngr.devsettings.util.debug.DebugDrawerHelper;
import io.palaima.debugdrawer.DebugDrawer;

/**
 * BaseDebug-class used for initialization of the {@link DebugDrawer}.
 */
public abstract class DebugBaseActivity extends AppCompatActivity {

    /**
     * {@link DebugDrawerHelper} reference to use.
     */
    private DebugDrawerHelper debugDrawerHelper;

    /**
     * Overrides {@link android.support.v7.app.AppCompatActivity#setContentView(int)} to
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
     * Overrides {@link android.support.v7.app.AppCompatActivity#setContentView(View)} to
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
     * Overrides {@link android.support.v7.app.AppCompatActivity#setContentView(View, ViewGroup.LayoutParams)}
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
        if (debugDrawerHelper == null)
            debugDrawerHelper = new DebugDrawerHelper(this);
        debugDrawerHelper.initDebugDrawer();
    }

    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    @Override
    protected void onStart() {
        super.onStart();
        debugDrawerHelper.onStart();
    }


    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    @Override
    protected void onResume() {
        super.onResume();
        debugDrawerHelper.onResume();
    }


    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    @Override
    protected void onPause() {
        super.onPause();
        debugDrawerHelper.onPause();
    }


    /**
     * Attach {@link DebugDrawer} to lifecycle.
     */
    @Override
    protected void onStop() {
        super.onStop();
        debugDrawerHelper.onStop();
    }
}
