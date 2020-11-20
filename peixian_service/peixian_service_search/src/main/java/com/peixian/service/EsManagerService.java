package com.peixian.service;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
public interface EsManagerService {

    /**
     * 删除索引
     */
    void deleteIndexAndMapping();

    /**
     * 创建索引
     */
    void createIndexAndMapping();

    /**
     * 导入Sku
     */
    void importSkuBySpuId(String spuId);

    /**
     * 导入全部Sku
     */
    void importAll();

    /**
     * 删除Sku
     */
    void deleteBySpuId(String spuId);

}


