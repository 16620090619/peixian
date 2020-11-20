package com.peixian.goods.feign;

import com.peixian.goods.pojo.Sku;
import com.peixian.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author gzj
 * @description
 * @date 2020/11/17
 */
@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 调用名为goods的微服务里的同名接口服务
     * @param spuId
     * @return
     */
    @GetMapping("/findBySpuId/{spuId}")
    List<Sku> findBySpuId(@PathVariable String spuId);

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    Result findAll();
}
