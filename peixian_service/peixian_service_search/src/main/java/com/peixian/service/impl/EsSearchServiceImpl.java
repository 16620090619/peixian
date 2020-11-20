package com.peixian.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peixian.goods.feign.SkuFeign;
import com.peixian.goods.pojo.Sku;
import com.peixian.mapper.SearchMapper;
import com.peixian.pojo.Result;
import com.peixian.pojo.SkuInfo;
import com.peixian.pojo.StatusCode;
import com.peixian.service.EsManagerService;
import com.peixian.service.EsSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
@Service
public class EsSearchServiceImpl implements EsSearchService {

    private static final String BRAND_GROUP = "brandGroup";
    private static final String CATEGORY_GROUP = "categoryGroup";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public Map search(Map<String, String> searchMap) {
        Map resultMap = new HashMap<>(4);
        if (searchMap == null){
            return resultMap;
        }

        //构建综合搜索条件类,可以进行模糊、精确、范围搜索
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //构建顶级查询对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //1:根据搜索关键词在商品搜索页面的搜索框进行搜索
        //构建搜索条件 must=and should=or mustNot=not
        if (searchMap.get("keywords") != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",searchMap.get("keywords")).operator(Operator.AND));
        }else {
            return resultMap;
        }
        //添加查询条件
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        //2.1:根据品牌进行分组 类似于select brandName from tb_sku group by brandName
        //设置分组名和分组字段
        TermsAggregationBuilder brandGroupBuilder = AggregationBuilders.terms(BRAND_GROUP).field("brandName");
        //添加分组条件
        nativeSearchQueryBuilder.addAggregation(brandGroupBuilder);

        //3.1:根据分类进行分组 类似于select brandName from tb_sku group by categoryName
        //设置分组名和分组字段
        TermsAggregationBuilder categoryGroupBuilder = AggregationBuilders.terms(CATEGORY_GROUP).field("categoryName");
        //添加分组条件
        nativeSearchQueryBuilder.addAggregation(categoryGroupBuilder);


        //指定索引库
        IndexCoordinates index = IndexCoordinates.of("skuinfo");
        //执行搜索
        AggregatedPage<SkuInfo> skuInfos = elasticsearchRestTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, index);

        //2.2获得brand分组内容
        ParsedStringTerms brandTerms = (ParsedStringTerms)skuInfos.getAggregation(BRAND_GROUP);
        List<? extends Terms.Bucket> brandBuckets = brandTerms.getBuckets();
        if (brandBuckets.size()>0){
            //将分组内容放入brandList
            List<String> brandList = brandBuckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            resultMap.put("brandList",brandList);
        }

        //3.2获得category分组内容
        ParsedStringTerms categoryTerms = (ParsedStringTerms)skuInfos.getAggregation(CATEGORY_GROUP);
        List<? extends Terms.Bucket> categoryBuckets = categoryTerms.getBuckets();
        if (categoryBuckets.size()>0){
            //将分组内容放入categoryList
            List<String> categoryList = categoryBuckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            resultMap.put("categoryList",categoryList);
        }


        resultMap.put("rows",skuInfos.getContent());
        resultMap.put("total",skuInfos.getTotalElements());
        resultMap.put("pages",skuInfos.getTotalPages());

        return resultMap;
    }
}
