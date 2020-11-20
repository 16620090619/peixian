package com.peixian.mapper;

import com.peixian.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author gzj
 * @description Long为主键ID
 * @date 2020/11/17
 */
public interface SearchMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
