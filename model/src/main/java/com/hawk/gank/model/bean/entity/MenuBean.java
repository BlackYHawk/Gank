package com.hawk.gank.model.bean.entity;

/**
 * Created by heyong on 16/7/18.
 */
public class MenuBean {

    public int menuId;             // 菜单的ID

    public int titleRes;          // activity的标题

    public int iconRes;           // 菜单的图标

    public int menuTitleRes;      // 菜单的标题

    public MenuBean(int menuId, int titleRes, int iconRes, int menuTitleRes) {
        this.menuId = menuId;
        this.titleRes = titleRes;
        this.iconRes = iconRes;
        this.menuTitleRes = menuTitleRes;
    }
}
