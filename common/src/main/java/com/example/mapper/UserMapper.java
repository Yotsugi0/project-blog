package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 01:43:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

