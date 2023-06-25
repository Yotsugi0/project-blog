package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-05-25 01:47:41
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult pageTagAdd(Tag tag);

    ResponseResult pageTagDelete(String ids);

    ResponseResult getTag(Long id);

    ResponseResult pageTagUpdate(Tag tag);

    List<TagVo> listAllTag();
}
