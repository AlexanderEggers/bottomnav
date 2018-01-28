package org.bottomnav;

public class BottomNavMenuItem {

    private CharSequence title;
    private int icon;

    public BottomNavMenuItem(CharSequence title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public CharSequence getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
