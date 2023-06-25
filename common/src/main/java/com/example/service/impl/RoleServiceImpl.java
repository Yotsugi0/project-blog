package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.dto.RoleChangeStatusDto;
import com.example.domain.entity.Role;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.RoleVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.RoleMapper;
import com.example.service.MenuService;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-05-26 18:58:45
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(roleName)){
            queryWrapper.like(Role::getRoleName,roleName);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(Role::getStatus,status);
        }
        queryWrapper.orderBy(true,true,Role::getRoleSort);

        Page<Role> page = new Page<>();
        page(page,queryWrapper);

        List<Role> roles = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        PageVo pageVo = new PageVo(roleVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleChangeStatusDto roleDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Role::getStatus,roleDto.getStatus());
        updateWrapper.eq(Role::getId,roleDto.getRoleId());
        getBaseMapper().update(new Role(),updateWrapper);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
