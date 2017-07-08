package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import de.mchllngr.devsettings.R;
import io.mattcarroll.hover.HoverMenu;

/**
 * {@link HoverMenu} for showing the DevSettings.
 */
public class DevSettingsHoverMenu extends HoverMenu {

    private final Context context;
    private final Section section;

    @SuppressWarnings("UnusedAssignment")
    DevSettingsHoverMenu(@NonNull Context context) {
        this.context = context.getApplicationContext();
        section = new Section(
                new SectionId("0"),
                createTabView(),
                new DevSettingsHoverScreen(context)
        );
    }

    private View createTabView() {
        ImageView tabView = new ImageView(context);
        tabView.setImageResource(R.mipmap.ic_launcher);
        tabView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return tabView;
    }

    @Override
    public String getId() {
        return "DevSettingsHoverMenu";
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    @Nullable
    @Override
    public Section getSection(int index) {
        return section;
    }

    @Nullable
    @Override
    public Section getSection(@NonNull SectionId sectionId) {
        return getSection(0);
    }

    @NonNull
    @Override
    public List<Section> getSections() {
        return Collections.singletonList(section);
    }
}
