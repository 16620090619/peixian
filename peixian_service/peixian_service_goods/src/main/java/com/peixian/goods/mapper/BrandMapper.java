package com.peixian.goods.mapper;

import com.peixian.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author gzj
 * @description
 * @date 2020/11/5
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 根据分类名查询品牌列表
     * @param cateName 分类名称
     * @return
     */
    @Select("select * from tb_brand where id in (select brand_id from tb_category_brand where category_id in (select id from tb_category where name = #{cateName}))")
    List<Brand> findByCateName(@Param("cateName") String cateName);

}
