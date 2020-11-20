package com.peixian.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.peixian.goods.mapper.*;
import com.peixian.goods.pojo.*;
import com.peixian.goods.service.SpuService;
import com.peixian.util.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private IdWorker idWorker;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private Gson gson;

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 查询全部列表
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id){
        return  spuMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     * @param spu
     */
    @Override
    public void add(Spu spu){
        spuMapper.insert(spu);
    }


    /**
     * 修改
     * @param spu
     */
    @Override
    public void update(Spu spu){
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        spuMapper.deleteByPrimaryKey(id);
    }


    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap){
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spu> findPage(int page, int size){
        PageHelper.startPage(page,size);
        return (Page<Spu>)spuMapper.selectAll();
    }

    /**
     * 条件+分页查询
     * @param searchMap 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<Spu> findPage(Map<String,Object> searchMap, int page, int size){
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        return (Page<Spu>)spuMapper.selectByExample(example);
    }

    /**
     * 构建查询对象
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 主键
            if(searchMap.get("id")!=null && !"".equals(searchMap.get("id"))){
                criteria.andEqualTo("id",searchMap.get("id"));
           	}
            // 货号
            if(searchMap.get("sn")!=null && !"".equals(searchMap.get("sn"))){
                criteria.andEqualTo("sn",searchMap.get("sn"));
           	}
            // SPU名
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
           	}
            // 副标题
            if(searchMap.get("caption")!=null && !"".equals(searchMap.get("caption"))){
                criteria.andLike("caption","%"+searchMap.get("caption")+"%");
           	}
            // 图片
            if(searchMap.get("image")!=null && !"".equals(searchMap.get("image"))){
                criteria.andLike("image","%"+searchMap.get("image")+"%");
           	}
            // 图片列表
            if(searchMap.get("images")!=null && !"".equals(searchMap.get("images"))){
                criteria.andLike("images","%"+searchMap.get("images")+"%");
           	}
            // 售后服务
            if(searchMap.get("saleService")!=null && !"".equals(searchMap.get("saleService"))){
                criteria.andLike("saleService","%"+searchMap.get("saleService")+"%");
           	}
            // 介绍
            if(searchMap.get("introduction")!=null && !"".equals(searchMap.get("introduction"))){
                criteria.andLike("introduction","%"+searchMap.get("introduction")+"%");
           	}
            // 规格列表
            if(searchMap.get("specItems")!=null && !"".equals(searchMap.get("specItems"))){
                criteria.andLike("specItems","%"+searchMap.get("specItems")+"%");
           	}
            // 参数列表
            if(searchMap.get("paraItems")!=null && !"".equals(searchMap.get("paraItems"))){
                criteria.andLike("paraItems","%"+searchMap.get("paraItems")+"%");
           	}
            // 是否上架
            if(searchMap.get("isMarketable")!=null && !"".equals(searchMap.get("isMarketable"))){
                criteria.andEqualTo("isMarketable",searchMap.get("isMarketable"));
           	}
            // 是否启用规格
            if(searchMap.get("isEnableSpec")!=null && !"".equals(searchMap.get("isEnableSpec"))){
                criteria.andEqualTo("isEnableSpec", searchMap.get("isEnableSpec"));
           	}
            // 是否删除
            if(searchMap.get("isDelete")!=null && !"".equals(searchMap.get("isDelete"))){
                criteria.andEqualTo("isDelete",searchMap.get("isDelete"));
           	}
            // 审核状态
            if(searchMap.get("status")!=null && !"".equals(searchMap.get("status"))){
                criteria.andEqualTo("status",searchMap.get("status"));
           	}

            // 品牌ID
            if(searchMap.get("brandId")!=null ){
                criteria.andEqualTo("brandId",searchMap.get("brandId"));
            }
            // 一级分类
            if(searchMap.get("category1Id")!=null ){
                criteria.andEqualTo("category1Id",searchMap.get("category1Id"));
            }
            // 二级分类
            if(searchMap.get("category2Id")!=null ){
                criteria.andEqualTo("category2Id",searchMap.get("category2Id"));
            }
            // 三级分类
            if(searchMap.get("category3Id")!=null ){
                criteria.andEqualTo("category3Id",searchMap.get("category3Id"));
            }
            // 模板ID
            if(searchMap.get("templateId")!=null ){
                criteria.andEqualTo("templateId",searchMap.get("templateId"));
            }
            // 运费模板id
            if(searchMap.get("freightId")!=null ){
                criteria.andEqualTo("freightId",searchMap.get("freightId"));
            }
            // 销量
            if(searchMap.get("saleNum")!=null ){
                criteria.andEqualTo("saleNum",searchMap.get("saleNum"));
            }
            // 评论数
            if(searchMap.get("commentNum")!=null ){
                criteria.andEqualTo("commentNum",searchMap.get("commentNum"));
            }

        }
        return example;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGoods(Goods goods) {

        Spu spu = goods.getSpu();
        spu.setId(String.valueOf(idWorker.nextId()));
        spuMapper.insertSelective(spu);


        saveSku(goods, spu);


    }

    private void saveSku(Goods goods, Spu spu) {
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());


        if(category!=null && brand!=null) {
            //处理分类与品牌表的关联关系，如果在中间表存在数据则忽略，如果不存在则新增关系数据
            CategoryBrand cb = new CategoryBrand();
            cb.setBrandId(brand.getId());
            cb.setCategoryId(category.getId());
            int count = categoryBrandMapper.selectCount(cb);
            if(count==0){
                categoryBrandMapper.insertSelective(cb);
            }
        }

        List<Sku> skuList = goods.getSkuList();
        if (skuList != null && skuList.size()>0){
            for (Sku sku : skuList) {
                sku.setId(String.valueOf(idWorker.nextId()));

                String goodsName = spu.getName();
                if(StringUtils.isEmpty(sku.getSpec())){
                    sku.setSpec("{}");
                }
                String spec = sku.getSpec();
                System.out.println(spec);
                Map<String,String> specMap = gson.fromJson(spec, Map.class);

                if(specMap.size()>0){
                    System.out.println(specMap);
                    for(String key : specMap.keySet()){
                        goodsName += " " + specMap.get(key);
                    }

                }

                sku.setName(goodsName);
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                sku.setSpuId(spu.getId());
                if(category!=null){
                    sku.setCategoryId(category.getId());
                    sku.setCategoryName(category.getName());
                }
                if(brand!=null){
                    sku.setBrandName(brand.getName());
                }
                skuMapper.insertSelective(sku);
            }
        }
    }

    @Override
    public Goods findBySpuId(String spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", spuId);

        List<Sku> skus = skuMapper.selectByExample(example);
        Goods goods = new Goods(spu,skus);

        return goods;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGoods(Goods goods) {
        spuMapper.updateByPrimaryKeySelective(goods.getSpu());

        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",goods.getSpu().getId());
        skuMapper.deleteByExample(example);

        saveSku(goods,goods.getSpu());
    }


    @Override
    public void auditGoods(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        spu.setStatus("1");
        spuMapper.updateByPrimaryKeySelective(spu);

    }

    @Override
    public void upGoods(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if("0".equals(spu.getStatus())){
            throw new RuntimeException("商品未通过审核");
        }
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);

        rabbitTemplate.convertAndSend("goods_up_exchange","",spuId);
    }

    @Override
    public void downGoods(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if("0".equals(spu.getIsMarketable())){
            throw new RuntimeException("商品未上架");
        }
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
        rabbitTemplate.convertAndSend("goods_down_exchange","",spuId);
    }

    @Override
    public void deleteLogic(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if("1".equals(spu.getIsMarketable())){
            throw new RuntimeException("商品未下架");
        }
        spu.setIsDelete("1");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void restore(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if("0".equals(spu.getIsDelete())){
            throw new RuntimeException("商品未逻辑删除");
        }
        spu.setIsDelete("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteReal(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if("1".equals(spu.getIsMarketable())){
            throw new RuntimeException("商品未下架");
        }
        spuMapper.deleteByPrimaryKey(spu);
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",spuId);
        skuMapper.deleteByExample(example);
    }
}
