package com.hawk.gank.util;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.MenuBean;

import java.util.ArrayList;

/**
 * Created by heyong on 16/7/18.
 */
public class MenuGenerator {

    public static ArrayList<MenuBean> generateMenus() {
        ArrayList<MenuBean> menuList = new ArrayList<MenuBean>();

        return menuList;
    }

    public static MenuBean generateMenu(int menuId) {
        MenuBean menuBean = null;

        switch (menuId) {
            // Gank信息
            case R.id.menu_pic:
                menuBean = new MenuBean(menuId, R.string.gank_list_title, R.mipmap.ic_github, R.string.menu_pic);
                break;
            // 开眼视频信息
            case R.id.menu_video:
                menuBean = new MenuBean(menuId, R.string.eye_list_title, R.mipmap.ic_github, R.string.menu_video);
                break;
            // 设置
            case R.id.menu_setting:
                menuBean = new MenuBean(menuId, R.string.setting_title, R.mipmap.ic_setting, R.string.menu_setting);
                break;
        }

        return menuBean;
    }

}
