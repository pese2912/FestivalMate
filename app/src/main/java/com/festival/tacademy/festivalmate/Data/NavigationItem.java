package com.festival.tacademy.festivalmate.Data;

/**
 * Created by J.K.Lee on 2016-06-07.
 */
public class NavigationItem {
    int id;
    int icon_res;
    String menu;

    public NavigationItem(int id, int icon_res, String menu) {
        this.id = id;
        this.icon_res = icon_res;
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
