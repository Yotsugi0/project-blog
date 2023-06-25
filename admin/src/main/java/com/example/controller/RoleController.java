package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.RoleChangeStatusDto;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String roleName, String status){
        return roleService.getRoleList(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeStatusDto roleDto){
        return roleService.changeStatus(roleDto);
    }
}
