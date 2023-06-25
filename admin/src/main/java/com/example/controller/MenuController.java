package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.entity.Menu;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(String status, String menuName){
        return menuService.menuList(status,menuName);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id){
        return menuService.getMenu(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Menu menu){
        return menuService.menuUpdate(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult delete(@PathVariable String menuId){
        return menuService.menuDelete(menuId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        return menuService.treeselect();
    }
}
