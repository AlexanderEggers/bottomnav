package org.bottomnav.examples

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import org.bottomnav.BottomNavMenuItem
import org.bottomnav.OnClickItemListener
import org.bottomnav.examples.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val items: ObservableList<BottomNavMenuItem> = ObservableArrayList()
    val listener: OnClickItemListener = object : OnClickItemListener {
        override fun onClickItem(v: View, position: Int) {
            System.out.println(position)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.activity = this

        items.add(BottomNavMenuItem(android.R.drawable.arrow_down_float, "Test 1"))
        items.add(BottomNavMenuItem(android.R.drawable.ic_menu_share, "Test 2"))
        items.add(BottomNavMenuItem(android.R.drawable.ic_input_get, "Test 3"))
        items.add(BottomNavMenuItem(android.R.drawable.ic_media_play, "Test 4"))
        items.add(BottomNavMenuItem(android.R.drawable.star_big_off, "Test 5"))
    }
}
