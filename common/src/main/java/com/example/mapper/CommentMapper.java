package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 07:51:52
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

