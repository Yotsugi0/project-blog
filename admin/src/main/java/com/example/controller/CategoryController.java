package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.AddCategoryDto;
import com.example.domain.entity.Category;
import com.example.domain.vo.CategoryVo;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status){
        return categoryService.categoryList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddCategoryDto categoryDto){
        return categoryService.add(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Category category){
        return categoryService.categoryUpdate(category);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult delete(@PathVariable String ids){
        return categoryService.categoryDelete(ids);
    }
}