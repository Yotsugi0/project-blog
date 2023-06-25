package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 01:25:33
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

