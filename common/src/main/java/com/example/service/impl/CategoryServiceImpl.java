package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddCategoryDto;
import com.example.domain.entity.Article;
import com.example.domain.entity.Category;
import com.example.domain.vo.ArticleVo;
import com.example.domain.vo.CategoryListVo;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.CategoryMapper;
import com.example.service.ArticleService;
import com.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.service.CategoryService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-05-22 20:16:37
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult categoryList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)){
            queryWrapper.like(Category::getName,name);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.like(Category::getStatus,status);
        }

        Page<Category> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<Category> categories = page.getRecords();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(categories, CategoryListVo.class);
        PageVo pageVo = new PageVo(categoryListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(AddCategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        return ResponseResult.okResult(category);
    }

    @Override
    public ResponseResult categoryUpdate(Category category) {
        updateById(category);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult categoryDelete(String ids) {
        for (String s : ids.split(",")) {
            removeById(s);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
