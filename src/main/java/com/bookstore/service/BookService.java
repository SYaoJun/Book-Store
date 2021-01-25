package com.bookstore.service;


import com.alibaba.fastjson.JSON;
import com.bookstore.utils.ESconstant;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojun
 * @create 2021-01-25 14:06
 */
@Service
public class BookService {
    @Autowired
    @Qualifier("restHighLevelClient")   //配置类中的方法名
    private RestHighLevelClient client;


    public List queryDocument(String keyword)throws Exception{
        SearchRequest request = new SearchRequest(ESconstant.BOOK_INDEX);
        //构建搜索条件
        SearchSourceBuilder searchSource = new SearchSourceBuilder();
        //查询语句
        //QueryBuilders.termQuery() 精确匹配
        //QueryBuilders.matchAllQuery() 匹配全部

//        QueryBuilder query = QueryBuilders.termQuery("name", "毛泽东选集");
        QueryBuilder query = QueryBuilders.matchQuery("name", keyword);
//        QueryBuilder query = QueryBuilders.matchAllQuery();
        searchSource.query(query);
        //searchSource.from(); 构建分页
        //searchSource.size();

        HighlightBuilder hiBuilder=new HighlightBuilder();
        hiBuilder.preTags("<span style='color:red'>");
        hiBuilder.postTags("</span>");
        hiBuilder.field("name");
        /*关闭多个高亮显示*/
        hiBuilder.requireFieldMatch(false);

        searchSource.from(0);
        searchSource.size(5);

        searchSource.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(searchSource);
        searchSource.highlighter(hiBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(response.getHits()));
        System.out.println("====================");
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if(name != null){
                Text[] fragments = name.fragments();
                String n_name = "";
                for (Text fragment : fragments) {
                    n_name += fragment;
                }
                sourceAsMap.put("name",n_name);
            }
            list.add(sourceAsMap);
        }
        list.forEach(e-> System.out.println(e));
        return list;
    }

    public List bookQueryAll() throws IOException {

        SearchRequest request = new SearchRequest(ESconstant.BOOK_INDEX);
        //构建搜索条件
        SearchSourceBuilder searchSource = new SearchSourceBuilder();

        QueryBuilder query = QueryBuilders.matchAllQuery();
        searchSource.query(query);

        //分页
        searchSource.from(0);
        searchSource.size(10);

        searchSource.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(searchSource);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            list.add(hit.getSourceAsMap());
        }
        return list;
    }
}
