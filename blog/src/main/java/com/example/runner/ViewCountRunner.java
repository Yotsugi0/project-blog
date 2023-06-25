package com.example.runner;

import com.example.domain.entity.Article;
import com.example.mapper.ArticleMapper;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 更新浏览次数
 * 在应用启动时把博客的浏览量存储到redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();//
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}