package com.peixian.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.peixian.goods.pojo.Brand;
import com.peixian.goods.mapper.BrandMapper;
import com.peixian.goods.service.BrandService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/5
 */
@Service
public class BrandServiceImpl implements BrandService {

    public static final String NAME = "name";
    public static final String LETTER = "letter";
    @Resource
    BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<Brand> searchPage(Map<String, String> paramMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        //设置查询条件
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (paramMap!=null && paramMap.size() > 0){
            //设置品牌名称模糊查询
            if (paramMap.get(NAME)!=null && !"".equals(paramMap.get(NAME))){
                criteria.andLike(NAME,"%"+paramMap.get(NAME)+"%");
            }
            //设置品牌首字母的精确查询
            if (paramMap.get(LETTER)!=null && !"".equals(paramMap.get(LETTER))){
                criteria.andEqualTo(LETTER,paramMap.get(LETTER));
            }
        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return pageInfo;
    }

    @Override
    public List<Brand> findByCateName(String cateName) {
        return brandMapper.findByCateName(cateName);
    }
}
