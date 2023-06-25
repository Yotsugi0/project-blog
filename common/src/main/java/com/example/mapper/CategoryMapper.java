package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-22 20:16:31
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

