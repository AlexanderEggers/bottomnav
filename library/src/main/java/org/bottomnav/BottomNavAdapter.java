package org.bottomnav;

import android.databinding.BindingAdapter;
import android.view.Menu;

import java.util.List;

public class BottomNavAdapter {

    @BindingAdapter("setOnClickItem")
    public static void onOnClickItem(BottomNavMenu bottomNavMenu, OnClickItemListener listener) {
        bottomNavMenu.setOnItemClickListener(listener);
    }

    @BindingAdapter("setItems")
    public static void setMenuItems(BottomNavMenu bottomNavMenu, Menu menu) {
        bottomNavMenu.setItems(menu);
    }

    @BindingAdapter("setItems")
    public static void setMenuItems(BottomNavMenu bottomNavMenu, List<BottomNavMenuItem> itemList) {
        bottomNavMenu.setItems(itemList);
    }
}
