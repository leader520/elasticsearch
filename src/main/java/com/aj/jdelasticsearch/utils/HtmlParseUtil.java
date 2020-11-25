package com.aj.jdelasticsearch.utils;

import com.aj.jdelasticsearch.pojo.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aj
 * jsoup解析网页
*/
@Component
public class HtmlParseUtil {

    public List<Product> parseJD(String keyword) {
        String url = "https://search.jd.com/Search?keyword="+keyword;
        List<Product> goodsList = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(url),30000);
            Element element = document.getElementById("J_goodsList");
            //获取所有的li元素
            Elements elements = element.getElementsByTag("li");
            //获取元素中需要的内容，图片，标题，价格
            for (Element el : elements) {
                //获取名称
                String name = el.getElementsByClass("p-name").eq(0).text();
                //获取图片，对于图片多的网站，采用的是延时加载
                String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
                //获取价格
                String price = el.getElementsByClass("p-price").eq(0).text();

                goodsList.add(new Product(name,price,img));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    return goodsList;
    }
}
