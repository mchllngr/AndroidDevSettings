package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.mchllngr.devsettings.R;
import io.mattcarroll.hover.HoverMenu;

/**
 * {@link HoverMenu} for showing multiple sections.
 */
public class MultiSectionHoverMenu extends HoverMenu {

    private final Context context;
    private final List<Section> sections;

    public MultiSectionHoverMenu(@NonNull Context context) {
        this.context = context.getApplicationContext();

        sections = Arrays.asList(
                new Section(
                        new SectionId("1"),
                        createTabView(),
                        new HoverMenuScreen(this.context, "Screen 1")
                ),
                new Section(
                        new SectionId("2"),
                        createTabView(),
                        new HoverMenuScreen(this.context, "Screen 2")
                ),
                new Section(
                        new SectionId("3"),
                        createTabView(),
                        new HoverMenuScreen(this.context, "Screen 3")
                )
        );
    }

    private View createTabView() {
        Resources resources = context.getResources();

        TabView view = new TabView(
                context,
                resources.getDrawable(R.drawable.tab_background), // TODO set something else
                resources.getDrawable(R.mipmap.ic_launcher)
        );
        view.setTabBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark)); // TODO set something else ?
        view.setTabForegroundColor(null);
        return view;
    }

    @Override
    public String getId() {
        return "multisectionmenu";
    }

    @Override
    public int getSectionCount() {
        return sections.size();
    }

    @Nullable
    @Override
    public Section getSection(int index) {
        return sections.get(index);
    }

    @Nullable
    @Override
    public Section getSection(@NonNull SectionId sectionId) {
        for (Section section : sections) {
            if (section.getId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public List<Section> getSections() {
        return new ArrayList<>(sections);
    }
}
