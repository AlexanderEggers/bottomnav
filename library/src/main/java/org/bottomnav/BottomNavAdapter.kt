package org.bottomnav

import android.databinding.BindingAdapter

object BottomNavAdapter {

    @JvmStatic
    @BindingAdapter("setOnClickItem")
    fun onOnClickItem(bottomNavMenu: BottomNavMenu, listener: OnClickItemListener) {
        bottomNavMenu.addOnItemClickListener(listener)
    }

    @JvmStatic
    @BindingAdapter("setItems")
    fun setItems(bottomNavMenu: BottomNavMenu, itemList: List<BottomNavMenuItem>) {
        bottomNavMenu.setItems(itemList)
    }
}
