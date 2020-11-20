package com.peixian.goods.service;

import com.github.pagehelper.Page;
import com.peixian.goods.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/5
 */
public interface BrandService {

    /**
     * 查找所有品牌
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据ID查找品牌
     * @param id id
     * @return
     */
    Brand findById(Integer id);

    /**
     * 添加品牌
     * @param brand brand
     * @return
     */
    void add(Brand brand);

    /**
     * 更新品牌
     * @param brand brand
     * @return
     */
    void update(Brand brand);

    /**
     * 删除品牌
     * @param id
     */
    void delete(Integer id);

    /**
     *根据条件分页查询
     * @param paramMap 参数列表
     * @param pageNum   第几页
     * @param pageSize  显示多少条
     * @return
     */
    Page<Brand> searchPage(Map<String, String> paramMap, int pageNum, int pageSize);

    /**
     * 根据分类名查询品牌列表
     * @param cateName 分类名称
     * @return
     */
    List<Brand> findByCateName(String cateName);
}
