package org.bottomnav;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BottomNavMenu extends LinearLayout implements View.OnClickListener {

    private List<OnClickItemListener> externalListeners = new ArrayList<>();

    private LayoutInflater inflater;

    private Drawable itemColor;

    private List<View> itemViews = new ArrayList<>();
    private SparseArray<CharSequence> bottomNavMap = new SparseArray<>();

    public BottomNavMenu(Context context) {
        super(context);
        initComponent();
    }

    public BottomNavMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
        if (attrs != null) initAttributes(attrs);
    }

    private void initComponent() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate(getContext(), R.layout.bottomnav_layout, null);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.bottomnav,
                0, 0);

        try {
            itemColor = a.getDrawable(R.styleable.bottomnav_itemColor);

            int backgroundColor = a.getColor(R.styleable.bottomnav_backgroundColor, 0);
            setBackgroundColor(backgroundColor);
        } finally {
            a.recycle();
        }
    }

    public void addOnItemClickListener(@NonNull OnClickItemListener listener) {
        externalListeners.add(listener);
        connectClickListener();
    }

    public void setItems(@NonNull List<BottomNavMenuItem> bottomNavMenuItems) {
        createBottomNavItems(bottomNavMenuItems);
        connectClickListener();
    }

    private void createBottomNavItems(List<BottomNavMenuItem> bottomNavMenuItems) {
        for (BottomNavMenuItem item : bottomNavMenuItems) {
            attachBottomNavItem(item.getTitle(), ContextCompat.getDrawable(getContext(), item.getIcon()));
        }
    }

    private void attachBottomNavItem(CharSequence title, Drawable iconDrawable) {
        View itemView = inflater.inflate(R.layout.bottomnav_item, null, false);

        ImageView icon = itemView.findViewById(R.id.icon);
        TextView text = itemView.findViewById(R.id.title);

        icon.setImageDrawable(iconDrawable);
        text.setText(title);
        itemView.setBackground(itemColor);

        bottomNavMap.put(itemView.hashCode(), title);
        itemViews.add(itemView);
        addView(itemView);
    }

    private void connectClickListener() {
        for (View view : itemViews) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int position = itemViews.indexOf(v);
        if (position != -1) {
            for (OnClickItemListener listener : externalListeners) {
                listener.onClickItem(v, 0);
            }
        } else {
            Log.d(BottomNavMenu.class.toString(), "Cannot execute onClick. Reason: Cannot find menu item.");
        }
    }
}
