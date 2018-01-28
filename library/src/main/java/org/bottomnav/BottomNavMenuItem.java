package org.bottomnav;

public class BottomNavMenuItem {

    private CharSequence title;
    private int icon;

    public static BottomNavMenuItem create() {
        return new BottomNavMenuItem();
    }

    public BottomNavMenuItem setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public BottomNavMenuItem setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public CharSequence getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
