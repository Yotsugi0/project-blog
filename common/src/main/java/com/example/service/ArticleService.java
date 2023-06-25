package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticle(Long id);

    ResponseResult articleUpdate(Article article);

    ResponseResult articleDelete(String ids);
}