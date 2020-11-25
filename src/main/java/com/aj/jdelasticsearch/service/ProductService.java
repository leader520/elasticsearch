package com.aj.jdelasticsearch.service;

import com.aj.jdelasticsearch.pojo.Product;
import com.aj.jdelasticsearch.utils.HtmlParseUtil;
import com.alibaba.fastjson.JSON;
import lombok.val;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author aj
 * 业务编写
*/
@Service
public class ProductService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private HtmlParseUtil htmlParseUtil;

    /**
     *解析数据放入es索引中
    */
    public Boolean parseProduct(String keyword) throws Exception {
       List<Product> products =  htmlParseUtil.parseJD(keyword);
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0;i < products.size();i++) {
            bulkRequest.add(
                    new IndexRequest("jd_products")
                    .id(""+(i+1))
                    .source(JSON.toJSONString(products.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    /**
     *获取数据实现搜索功能
    */
    public List<Map<String,Object>> searchPage(String keyword,int pageNo,int pageSize) throws Exception{
        if(pageNo<=1) {
            pageNo = 1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("jd_products");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name",keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //结果解析
        List<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            list.add(hit.getSourceAsMap());
        }

        return list;
    }

}
