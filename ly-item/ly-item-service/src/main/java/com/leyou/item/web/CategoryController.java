package com.leyou.item.web;

import com.leyou.item.service.CategoryService;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid){
//        ResponseEntity.status(HttpStatus.OK).body(null)
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    /*
    * 根据id查询商品分类
    * */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}
