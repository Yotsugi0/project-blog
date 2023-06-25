package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-05-26 19:01:22
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult menuList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenu(Long id);

    ResponseResult menuUpdate(Menu menu);

    ResponseResult menuDelete(String menuId);

    ResponseResult treeselect();
}
