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
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
@Service
public class EsManagerServiceImpl implements EsManagerService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private Gson gson;

    @Override
    public void deleteIndexAndMapping() {
        elasticsearchRestTemplate.deleteIndex(SkuInfo.class);
    }

    @Override
    public void createIndexAndMapping() {
        elasticsearchRestTemplate.createIndex(SkuInfo.class);
        elasticsearchRestTemplate.putMapping(SkuInfo.class);
    }

    @Override
    public void importSkuBySpuId(String spuId) {
        List<SkuInfo> skuInfoList = getSkuListBySpuId(spuId);
        if(skuInfoList.size()>0 && skuInfoList!=null){
            for (SkuInfo skuInfo : skuInfoList) {
                skuInfo.setSpecMap(gson.fromJson(skuInfo.getSpec(),new TypeToken<Map<String,Object>>() {}.getType()));
            }
        }

        searchMapper.saveAll(skuInfoList);
    }

    @Override
    public void importAll() {
        Result all = skuFeign.findAll();
        if(all.getCode() != StatusCode.OK){
            return;
        }
        String allJson = gson.toJson(all.getData());
        List<SkuInfo> skuInfoList = gson.fromJson(allJson, new TypeToken<List<SkuInfo>>() {}.getType());

        if(skuInfoList.size()>0 && skuInfoList!=null){
            for (SkuInfo skuInfo : skuInfoList) {
                skuInfo.setSpecMap(gson.fromJson(skuInfo.getSpec(),new TypeToken<Map<String,Object>>() {}.getType()));
            }
        }

        searchMapper.saveAll(skuInfoList);
    }

    @Override
    public void deleteBySpuId(String spuId) {
        List<SkuInfo> skuInfoList = getSkuListBySpuId(spuId);
        searchMapper.deleteAll(skuInfoList);
    }

    private List<SkuInfo> getSkuListBySpuId(String spuId) {
        List<Sku> skuList = skuFeign.findBySpuId(spuId);
        String skuListJson = gson.toJson(skuList);
        return gson.fromJson(skuListJson, new TypeToken<List<SkuInfo>>() {
        }.getType());
    }

}
