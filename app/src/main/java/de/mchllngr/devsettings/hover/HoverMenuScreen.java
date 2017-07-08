/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import io.mattcarroll.hover.Content;

/**
 * Example screen that is displayed in the hover-menu. Will be replaced by real screens.
 *
 * TODO: not fullscreen ? (see NonFullscreenContent in hoverdemo-nonfullscreen)
 */
public class HoverMenuScreen implements Content {

    private final Context context;
    private final String pageTitle;
    private final View view;

    public HoverMenuScreen(@NonNull Context context, @NonNull String pageTitle) {
        this.context = context.getApplicationContext();
        this.pageTitle = pageTitle;
        view = createScreenView();
    }

    @NonNull
    private View createScreenView() {
        TextView wholeScreen = new TextView(context);
        wholeScreen.setText("Screen: " + pageTitle);
        wholeScreen.setGravity(Gravity.CENTER);
        return wholeScreen;
    }

    // Make sure that this method returns the SAME View. It should NOT create a new View each time that it is invoked.
    @NonNull
    @Override
    public View getView() {
        return view;
    }

    @Override
    public boolean isFullscreen() {
        return true;
    }

    @Override
    public void onShown() {
        // TODO
    }

    @Override
    public void onHidden() {
        // TODO
    }
}
