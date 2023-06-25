package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.AddLinkDto;
import com.example.domain.entity.Link;
import com.example.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.linkList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddLinkDto LinkDto){
        return linkService.add(LinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return linkService.getLink(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Link link){
        return linkService.linkUpdate(link);
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        return linkService.changeLinkStatus(link);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult delete(@PathVariable String ids){
        return linkService.linkDelete(ids);
    }
}
