package com.leyou.search.client;

import com.leyou.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

//    @GetMapping("category/list/ids")
//    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);

}
