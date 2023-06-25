package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.TagMapper;
import com.example.service.TagService;
import com.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-25 01:47:41
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult pageTagAdd(Tag tag) {
        int i = getBaseMapper().insert(tag);
        if (i > 0){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(400,"添加失败");
    }

    @Override
    public ResponseResult pageTagDelete(String ids) {
        /*LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Tag::getDelFlag,1);
        updateWrapper.eq(Tag::getId,id);
        int i = getBaseMapper().update(new Tag(),updateWrapper);
        if (i > 0){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(400,"删除失败");*/
        for (String s : ids.split(",")) {
            removeById(s);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getTag(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName,Tag::getRemark);
        queryWrapper.eq(Tag::getId,id);
        Tag tag = getBaseMapper().selectOne(queryWrapper);
        if (!Objects.isNull(tag)){
            return ResponseResult.okResult(tag);
        }
        return ResponseResult.errorResult(400,"失败");
    }

    @Override
    public ResponseResult pageTagUpdate(Tag tag) {
        int i = getBaseMapper().updateById(tag);
        if (i > 0){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(400,"修改失败");
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }
}
