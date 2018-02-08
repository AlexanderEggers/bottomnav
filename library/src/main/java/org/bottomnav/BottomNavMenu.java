package org.bottomnav;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BottomNavMenu extends LinearLayout implements View.OnClickListener {

    private static final String TAG = BottomNavMenu.class.getName();
    private static final int MAX_SUPPORT_ITEMS = 5;

    private final List<OnClickItemListener> externalListeners = new ArrayList<>();
    private final List<Integer> itemViews = new ArrayList<>();

    private LayoutInflater inflater;

    private Integer itemBackgroundColor;
    private int itemBackground;

    private Drawable menuBackground;
    private int menuBackgroundColor;

    private ColorStateList colorStateList;
    private float textSize;

    public BottomNavMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        readAttributes(context, attrs);
        initComponent(context);
    }

    public BottomNavMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(context, attrs);
        initComponent(context);

        if(menuBackgroundColor != -1) {
            setBackgroundColor(menuBackgroundColor);
        } else {
            setBackground(menuBackground);
        }
    }

    protected void readAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.bottomnav,
                0, 0);

        try {
            colorStateList = a.getColorStateList(R.styleable.bottomnav_textColor);

            itemBackgroundColor = a.getColor(R.styleable.bottomnav_itemBackgroundColor, -1);
            itemBackground = a.getResourceId(R.styleable.bottomnav_itemBackground, -1);

            menuBackground = a.getDrawable(R.styleable.bottomnav_menuBackground);
            menuBackgroundColor = a.getColor(R.styleable.bottomnav_itemBackgroundColor, -1);

            textSize = a.getDimension(R.styleable.bottomnav_textSize,-1);
        } finally {
            a.recycle();
        }
    }

    protected void initComponent(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate(context, R.layout.bottomnav_layout, null);
    }

    public void addOnItemClickListener(@NonNull OnClickItemListener...listener) {
        externalListeners.addAll(Arrays.asList(listener));
    }

    public void setItems(@NonNull List<BottomNavMenuItem> bottomNavMenuItems) {
        createBottomNavItems(bottomNavMenuItems);
    }

    protected void createBottomNavItems(List<BottomNavMenuItem> itemList) {
        for (int i = 0; i < itemList.size() && i < MAX_SUPPORT_ITEMS; i++) {
            BottomNavMenuItem item = itemList.get(i);
            attachBottomNavItem(item.getTitle(), ContextCompat.getDrawable(getContext(), item.getIcon()));
        }

        if(itemList.size() > MAX_SUPPORT_ITEMS) {
            Log.d(TAG, "The BottomNavMenu component only supports up to five different menu items.");
        }
    }

    protected void attachBottomNavItem(CharSequence title, Drawable iconDrawable) {
        View itemView = inflater.inflate(R.layout.bottomnav_item, this, false);

        ImageView icon = itemView.findViewById(R.id.icon);
        TextView text = itemView.findViewById(R.id.title);

        icon.setImageDrawable(iconDrawable);
        text.setText(title);
        text.setTextColor(colorStateList);

        if(textSize != -1) {
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        if(itemBackgroundColor != -1) {
            itemView.setBackgroundColor(itemBackgroundColor);
        } else {
            itemView.setBackgroundResource(itemBackground);
        }

        itemView.setOnClickListener(this);

        itemViews.add(itemView.hashCode());
        addView(itemView);
    }

    @Override
    public void onClick(View v) {
        int position = itemViews.indexOf(v.hashCode());
        if (position != -1) {
            for (OnClickItemListener listener : externalListeners) {
                listener.onClickItem(v, position);
            }
        } else {
            Log.e(TAG, "Cannot execute onClick. Reason: Cannot find menu item.");
        }
    }
}
