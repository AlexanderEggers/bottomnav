package org.bottomnav

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList
import java.util.Arrays

class BottomNavMenu : LinearLayout, View.OnClickListener {

    private val externalListeners = ArrayList<OnClickItemListener>()
    private val itemViews = ArrayList<Int>()

    private var inflater: LayoutInflater? = null

    private var itemBackgroundColor: Int? = null
    private var itemBackground: Int = 0

    private var menuBackground: Drawable? = null
    private var menuBackgroundColor: Int = 0

    private var colorStateList: ColorStateList? = null
    private var textSize: Float = 0.toFloat()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        readAttributes(context, attrs)
        initComponent(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        readAttributes(context, attrs)
        initComponent(context)

        if (menuBackgroundColor != -1) {
            setBackgroundColor(menuBackgroundColor)
        } else {
            background = menuBackground
        }
    }

    private fun readAttributes(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.bottomnav,
                0, 0)

        try {
            colorStateList = a.getColorStateList(R.styleable.bottomnav_textColor)

            itemBackgroundColor = a.getColor(R.styleable.bottomnav_itemBackgroundColor, -1)
            itemBackground = a.getResourceId(R.styleable.bottomnav_itemBackground, -1)

            menuBackground = a.getDrawable(R.styleable.bottomnav_menuBackground)
            menuBackgroundColor = a.getColor(R.styleable.bottomnav_itemBackgroundColor, -1)

            textSize = a.getDimension(R.styleable.bottomnav_textSize, -1f)
        } finally {
            a.recycle()
        }
    }

    private fun initComponent(context: Context) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        View.inflate(context, R.layout.bottomnav_layout, null)
    }

    fun addOnItemClickListener(vararg listener: OnClickItemListener) {
        externalListeners.addAll(Arrays.asList(*listener))
    }

    fun setItems(bottomNavMenuItems: List<BottomNavMenuItem>) {
        createBottomNavItems(bottomNavMenuItems)
    }

    private fun createBottomNavItems(itemList: List<BottomNavMenuItem>) {
        if (itemList.size > MAX_SUPPORT_ITEMS) {
            Log.d(TAG, "The BottomNavMenu component only supports up to five different menu items.")
        }

        var i = 0
        while (i < itemList.size && i < MAX_SUPPORT_ITEMS) {
            val item = itemList[i]
            attachBottomNavItem(item.title, ContextCompat.getDrawable(context, item.icon))
            i++
        }
    }

    private fun attachBottomNavItem(title: CharSequence?, iconDrawable: Drawable?) {
        val itemView = inflater!!.inflate(R.layout.bottomnav_item, this, false)

        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val text = itemView.findViewById<TextView>(R.id.title)

        icon.setImageDrawable(iconDrawable)
        text.text = title
        text.setTextColor(colorStateList)

        if (textSize != -1f) {
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

        if (itemBackgroundColor != -1) {
            itemView.setBackgroundColor(itemBackgroundColor!!)
        } else {
            itemView.setBackgroundResource(itemBackground)
        }

        itemView.setOnClickListener(this)

        itemViews.add(itemView.hashCode())
        addView(itemView)
    }

    override fun onClick(v: View) {
        val position = itemViews.indexOf(v.hashCode())
        if (position != -1) {
            for (listener in externalListeners) {
                listener.onClickItem(v, position)
            }
        } else {
            Log.e(TAG, "Cannot execute onClick. Reason: Cannot find menu item.")
        }
    }

    companion object {
        private val TAG = BottomNavMenu::class.java.name
        private const val MAX_SUPPORT_ITEMS = 5
    }
}
