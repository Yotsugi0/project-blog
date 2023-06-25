package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /*@GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list());
    }*/

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Tag tag){
        return tagService.pageTagAdd(tag);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult delete(@PathVariable String ids){
        return tagService.pageTagDelete(ids);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable Long id){
        return tagService.getTag(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Tag tag){
        return tagService.pageTagUpdate(tag);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}