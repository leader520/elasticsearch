package com.aj.jdelasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author aj
 * 商品
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private String price;
    private String img;
}
