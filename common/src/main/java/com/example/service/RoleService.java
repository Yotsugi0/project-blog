package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.RoleChangeStatusDto;
import com.example.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-26 18:58:45
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleChangeStatusDto roleDto);
}
