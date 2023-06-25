package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.entity.Menu;
import com.example.domain.vo.MenuTreeVo;
import com.example.domain.vo.MenuVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.MenuMapper;
import com.example.service.MenuService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-05-26 19:01:22
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult menuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(status)){
            queryWrapper.eq(Menu::getStatus,status);
        }
        if (StringUtils.hasText(menuName)){
            queryWrapper.like(Menu::getMenuName,menuName);
        }
        queryWrapper.orderBy(true,true,Menu::getParentId,Menu::getOrderNum);
        List<Menu> menuList = getBaseMapper().selectList(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getMenu(Long id) {
        Menu menu = getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult menuUpdate(Menu menu) {
        if (menu.getParentId() == menu.getId()){
            return ResponseResult.errorResult(500,"修改菜单'写博文'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult menuDelete(String menuId) {
        for (String s : menuId.split(",")) {
            removeById(s);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult treeselect() {
        //todo
        return ResponseResult.errorResult(400,"功能未完成");
    }
}
