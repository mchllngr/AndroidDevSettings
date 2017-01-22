package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.mattcarroll.hover.HoverMenuAdapter;
import io.mattcarroll.hover.NavigatorContent;
import io.mattcarroll.hover.defaulthovermenu.menus.DoNothingMenuAction;
import io.mattcarroll.hover.defaulthovermenu.menus.Menu;
import io.mattcarroll.hover.defaulthovermenu.menus.MenuItem;
import io.mattcarroll.hover.defaulthovermenu.menus.MenuListNavigatorContent;
import io.mattcarroll.hover.defaulthovermenu.menus.ShowSubmenuMenuAction;
import io.mattcarroll.hover.defaulthovermenu.toolbar.ToolbarNavigatorContent;

/**
 * {@link HoverMenuAdapter} used in {@link DevSettingsHoverMenuService} for showing the floating menu.
 */
class DevSettingsHoverMenuAdapter implements HoverMenuAdapter {

    public static final String ID_ONE = "ONE";
    public static final String ID_TWO = "TWO";
    public static final String ID_THREE = "THREE";
    public static final String ID_FOUR = "FOUR";
    public static final String ID_FIVE = "FIVE";

    private Context context;
    private List<String> tabs;
    private Map<String, NavigatorContent> contentMap;

    DevSettingsHoverMenuAdapter(Context context) {
        this.context = context;
        tabs = Arrays.asList(ID_ONE, ID_TWO, ID_THREE, ID_FOUR, ID_FIVE);
        initContentMap();
    }

    private void initContentMap() {
        // TODO change
        // from: https://github.com/google/hover/blob/master/hoverdemo/src/main/java/io/mattcarroll/hover/hoverdemo/DemoHoverMenuFactory.java

        Menu drillDownMenuLevelTwo = new Menu("Demo Menu - Level 2", Arrays.asList(
                new MenuItem(UUID.randomUUID().toString(), "Google", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(), "Amazon", new DoNothingMenuAction())
        ));
        ShowSubmenuMenuAction showLevelTwoMenuAction = new ShowSubmenuMenuAction(drillDownMenuLevelTwo);

        Menu drillDownMenu = new Menu("Demo Menu", Arrays.asList(
                new MenuItem(UUID.randomUUID().toString(), "GPS", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(), "Cell Tower Triangulation", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(), "Location Services", showLevelTwoMenuAction)
        ));

        MenuListNavigatorContent drillDownMenuNavigatorContent = new MenuListNavigatorContent(context, drillDownMenu);

        ToolbarNavigatorContent toolbarNavigatorContent = new ToolbarNavigatorContent(context);
        toolbarNavigatorContent.pushContent(drillDownMenuNavigatorContent);

        Map<String, NavigatorContent> demoMenu = new LinkedHashMap<>();
        demoMenu.put(ID_ONE, toolbarNavigatorContent);//new HoverIntroductionNavigatorContent(context, Bus.getInstance()));
        demoMenu.put(ID_TWO, toolbarNavigatorContent);//new ColorSelectionNavigatorContent(context, Bus.getInstance(), HoverThemeManager.getInstance(), HoverThemeManager.getInstance().getTheme()));
        demoMenu.put(ID_THREE, toolbarNavigatorContent);//new AppStateNavigatorContent(context));
        demoMenu.put(ID_FOUR, toolbarNavigatorContent);
        demoMenu.put(ID_FIVE, toolbarNavigatorContent);//new PlaceholderNavigatorContent(context, bus));

        contentMap = demoMenu;
    }

    @Override
    public int getTabCount() {
        return tabs.size();
    }

    @Override
    public long getTabId(int position) {
        return tabs.get(position).hashCode();
    }

    @Override
    public View getTabView(int position) {
        // TODO
        // https://github.com/google/hover/blob/master/hoverdemo/src/main/java/io/mattcarroll/hover/hoverdemo/DemoHoverMenuAdapter.java

//        String tabName = tabs.get(position);
//        if ("first".equals(tabName)) {
//            // Create and return the tab View for "first".
//        } else if ("second".equals(tabName)) {
//            // Create and return the tab View for "second".
//        }
//        // etc.

        return new View(context);
    }

    @Override
    public NavigatorContent getNavigatorContent(int position) {
        String tabName = tabs.get(position);
        return contentMap.get(tabName);
    }

    @Override
    public void addContentChangeListener(@NonNull ContentChangeListener listener) {
        // TODO ???
        // https://github.com/google/hover/blob/master/hoverdemo/src/main/java/io/mattcarroll/hover/hoverdemo/DemoHoverMenuAdapter.java
    }

    @Override
    public void removeContentChangeListener(@NonNull ContentChangeListener listener) {
        // TODO ???
        // https://github.com/google/hover/blob/master/hoverdemo/src/main/java/io/mattcarroll/hover/hoverdemo/DemoHoverMenuAdapter.java
    }
}
