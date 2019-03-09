package com.example.gradplanproject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import androidx.appcompat.widget.Toolbar;

public class DrawerUtil {

    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerViewCourses = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.view_courses).withIcon(android.R.drawable.ic_menu_more);
        PrimaryDrawerItem drawerSearch = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.search_courses).withIcon(android.R.drawable.ic_search_category_default);
        PrimaryDrawerItem drawerSchedule = new PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.view_schedule).withIcon(android.R.drawable.ic_menu_my_calendar);
        PrimaryDrawerItem drawerSettings = new PrimaryDrawerItem().withIdentifier(4)
                .withName(R.string.drawer_settings).withIcon(android.R.drawable.ic_menu_preferences);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .build();

        Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        new DividerDrawerItem(),
                        drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawerViewCourses,
                        drawerSearch,
                        drawerSchedule,
                        drawerEmptyItem, drawerEmptyItem, drawerEmptyItem,
                        new DividerDrawerItem(),
                        drawerSettings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1 && !(activity instanceof CourseViewActivity)) {
                            Intent intent = new Intent(activity, CourseViewActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 2 && !(activity instanceof SearchActivity)) {
                            Intent intent = new Intent(activity, SearchActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 3 && !(activity instanceof ScheduleActivity)) {
                            Intent intent = new Intent(activity, ScheduleActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
        System.out.println("Drawer Built");
    }
}