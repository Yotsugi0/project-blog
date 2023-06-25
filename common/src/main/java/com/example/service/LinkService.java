package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddLinkDto;
import com.example.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 01:25:38
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult add(AddLinkDto LinkDto);

    ResponseResult getLink(Long id);

    ResponseResult linkUpdate(Link link);

    ResponseResult changeLinkStatus(Link link);

    ResponseResult linkDelete(String ids);
}
