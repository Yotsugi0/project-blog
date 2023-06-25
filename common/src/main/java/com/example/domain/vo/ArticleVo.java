package com.example.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
    private Date createTime;
    private String isComment;
    private String isTop;
    private String status;
}
