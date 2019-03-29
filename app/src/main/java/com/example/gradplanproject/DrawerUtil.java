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

/**
 * Contains code to build a navigation drawer using the MaterialDrawer library by Mike Penz.
 * This code can be called by any Activity wanting to create a Nav Drawer, and each activity
 * will contain the same drawer.
 *
 * License for MaterialDrawer:
 * Copyright 2018 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class DrawerUtil {

    /**
     * Builds the drawer for the passed activity, using the passed Toolbar.
     * @param activity The activity to use the drawer.
     * @param toolbar The toolbar to use drawer functionality (like hamburger icon).
     */
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
                .withHeaderBackground(R.drawable.ic_gpp_header)
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