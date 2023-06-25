package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entity.Article;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博文接口
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary){
        return articleService.getArticleList(pageNum,pageSize,title,summary);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Article article){
        return articleService.articleUpdate(article);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult delete(@PathVariable String ids){
        return articleService.articleDelete(ids);
    }
}