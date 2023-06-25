package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddCategoryDto;
import com.example.domain.entity.Category;
import com.example.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-05-22 20:16:36
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult categoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult add(AddCategoryDto categoryDto);

    ResponseResult getCategory(Long id);

    ResponseResult categoryUpdate(Category category);

    ResponseResult categoryDelete(String ids);
}
