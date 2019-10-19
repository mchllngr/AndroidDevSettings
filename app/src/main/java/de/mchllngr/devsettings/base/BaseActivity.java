package de.mchllngr.devsettings.base;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.mchllngr.devsettings.R;

/**
 * Base-class for work concerning every {@link android.app.Activity}.
 */
public abstract class BaseActivity extends DebugBaseActivity {

    /**
     * Overrides {@link AppCompatActivity#setSupportActionBar(Toolbar)} to
     * allow setting the default title when called.
     *
     * @param toolbar toolbar to set
     */
    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        setDefaultActionBarTitle();
    }

    /**
     * Sets the default title for the {@link ActionBar}.
     */
    private void setDefaultActionBarTitle() {
        setActionBarTitle(R.string.app_name);
    }

    /**
     * Sets the title for the {@link ActionBar} via the
     * given {@link StringRes}.
     * <p>
     * If the {@link ActionBar} is not set yet the function does nothing.
     *
     * @param titleResId {@link StringRes} for the title
     */
    public void setActionBarTitle(@StringRes int titleResId) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titleResId);
    }

    /**
     * Sets the visibility of the home-button (up-arrow).
     */
    public void setShowHomeButton(boolean showHomeButton) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeButton);
            getSupportActionBar().setDisplayShowHomeEnabled(showHomeButton);
        }
    }
}
