package org.bottomnav;

import android.databinding.BindingAdapter;
import android.view.Menu;

import java.util.List;

public class BottomNavAdapter {

    @BindingAdapter("setOnClickItem")
    public static void onOnClickItem(BottomNavMenu bottomNavMenu, OnClickItemListener listener) {
        bottomNavMenu.addOnItemClickListener(listener);
    }

    @BindingAdapter("setItems")
    public static void setItems(BottomNavMenu bottomNavMenu, List<BottomNavMenuItem> itemList) {
        bottomNavMenu.setItems(itemList);
    }
}
