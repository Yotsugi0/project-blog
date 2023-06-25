package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-25 01:47:39
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

