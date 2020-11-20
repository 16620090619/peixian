package com.peixian.service;

import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/19
 */
public interface EsSearchService {

    /**
     * 搜索Sku
     * @param searchMap
     * @return
     */
    Map search(Map<String,String> searchMap);

}
