package com.aj.jdelasticsearch.controller;

import com.aj.jdelasticsearch.api.CommonResult;
import com.aj.jdelasticsearch.pojo.Product;
import com.aj.jdelasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aj
 * 请求编写
*/
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception {
        return productService.parseProduct(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> search(@PathVariable("keyword") String keyword,
                                           @PathVariable("pageNo") int pageNo,
                                           @PathVariable("pageSize") int pageSize) throws Exception {
        return productService.searchPage(keyword,pageNo,pageSize);
    }

    @GetMapping("/search2/{keyword}/{pageNo}/{pageSize}")
    public CommonResult<List<Map<String,Object>>> search2(@PathVariable("keyword") String keyword,
                                           @PathVariable("pageNo") int pageNo,
                                           @PathVariable("pageSize") int pageSize) throws Exception {
        return CommonResult.success(productService.searchPage(keyword,pageNo,pageSize));
    }





}
