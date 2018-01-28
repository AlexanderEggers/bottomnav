package org.bottomnav;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BottomNavMenu extends LinearLayout implements View.OnClickListener {

    private OnClickItemListener externalListener;

    private LayoutInflater inflater;

    private int itemColor;

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

    public BottomNavMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
        if (attrs != null) initAttributes(attrs);
    }

    @RequiresApi(21)
    public BottomNavMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
            itemColor = a.getColor(R.styleable.bottomnav_itemColor, 0);

            int backgroundColor = a.getColor(R.styleable.bottomnav_backgroundColor, 0);
            setBackgroundColor(backgroundColor);
        } finally {
            a.recycle();
        }
    }

    public void setOnItemClickListener(@NonNull OnClickItemListener listener) {
        this.externalListener = listener;
        if (!itemViews.isEmpty()) connectClickListener();
    }

    public void setItems(@NonNull Menu menu) {
        createBottomNavItems(menu);
        if (externalListener != null) connectClickListener();
    }

    public void setItems(@NonNull List<BottomNavMenuItem> bottomNavMenuItems) {
        createBottomNavItems(bottomNavMenuItems);
        if (externalListener != null) connectClickListener();
    }

    private void createBottomNavItems(List<BottomNavMenuItem> bottomNavMenuItems) {
        for (BottomNavMenuItem item : bottomNavMenuItems) {
            attachBottomNavItem(item.getTitle(), ContextCompat.getDrawable(getContext(), item.getIcon()));
        }
    }

    private void createBottomNavItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            attachBottomNavItem(item.getTitle(), item.getIcon());
        }
    }

    private void attachBottomNavItem(CharSequence title, Drawable iconDrawable) {
        View itemView = inflater.inflate(R.layout.bottomnav_item, null, false);

        ImageView icon = itemView.findViewById(R.id.icon);
        TextView text = itemView.findViewById(R.id.title);

        icon.setImageDrawable(iconDrawable);
        text.setText(title);
        itemView.setBackgroundColor(itemColor);

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
        if (externalListener != null && position != -1) {
            externalListener.onClickItem(v, position);
        } else {
            Log.d(BottomNavMenu.class.toString(), "Cannot execute onClick. Reason: Cannot find menu item.");
        }
    }
}
