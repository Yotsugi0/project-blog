package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.UserInfoVo;
import com.example.domain.vo.UserVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-23 08:04:07
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userName)){
            queryWrapper.like(User::getUserName,userName);
        }
        if (StringUtils.hasText(phonenumber)){
            queryWrapper.like(User::getPhonenumber,phonenumber);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(User::getStatus,status);
        }

        Page<User> page = new Page<>();
        page(page,queryWrapper);

        List<User> users = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        PageVo pageVo = new PageVo(userVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
