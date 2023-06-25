package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddLinkDto;
import com.example.domain.entity.Link;
import com.example.domain.vo.CategoryListVo;
import com.example.domain.vo.LinkVo;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.LinkMapper;
import com.example.service.LinkService;
import com.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-05-23 01:25:39
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)){
            queryWrapper.like(Link::getName,name);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.like(Link::getStatus,status);
        }

        Page<Link> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<Link> links = page.getRecords();
        List<LinkVo> linkListVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        PageVo pageVo = new PageVo(linkListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(AddLinkDto LinkDto) {
        Link link = BeanCopyUtils.copyBean(LinkDto, Link.class);
        save(link);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = getById(id);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult linkUpdate(Link link) {
        updateById(link);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult changeLinkStatus(Link link) {
        updateById(link);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult linkDelete(String ids) {
        for (String s : ids.split(",")) {
            removeById(s);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
