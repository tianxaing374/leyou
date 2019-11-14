package com.leyou.item.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.service.ItemService;
import com.leyou.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //rest风格的异常处理和正常处理的返回
    @PostMapping()
    public ResponseEntity<Item> saveItem(Item item){
        //校验价格
        if(item.getPrice() == null){
            //CommonExceptionHandler在com.leyou.common下，
            // 而LyItemApplication在com.leyou下，所以会扫描到CommonExceptionHandler上的@ControllerAdvice注解
//            throw new RuntimeException("价格不能为空");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        item = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

}
