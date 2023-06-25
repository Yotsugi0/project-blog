package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-02 06:45:01
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

