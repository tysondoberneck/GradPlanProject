package com.example.gradplanproject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.appcompat.widget.Toolbar;

public class DrawerUtil {

    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerViewCourses = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.view_courses).withIcon(R.drawable.material_drawer_badge);
        PrimaryDrawerItem drawerSearch = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.search_courses).withIcon(R.drawable.material_drawer_badge);
        PrimaryDrawerItem drawerSchedule = new PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.view_schedule).withIcon(R.drawable.material_drawer_badge);


        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawerViewCourses,
                        drawerSearch,
                        drawerSchedule
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