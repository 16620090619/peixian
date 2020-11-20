package com.peixian.goods.controller;

import com.github.pagehelper.Page;
import com.peixian.goods.mapper.BrandMapper;
import com.peixian.goods.pojo.Spec;
import com.peixian.goods.service.BrandService;
import com.peixian.goods.pojo.Brand;
import com.peixian.pojo.PageResult;
import com.peixian.pojo.Result;
import com.peixian.pojo.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/5
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Resource
    BrandService brandService;
    @Resource
    BrandMapper brandMapper;

    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK,"查询全部品牌成功",brandList);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Brand brand = brandService.findById(id);
        return new Result(true, StatusCode.OK,"根据ID查询品牌成功",brand);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Brand brand){
//        int i = 1 / 0;
        brandService.add(brand);
        return new Result(true, StatusCode.OK,"添加品牌成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Brand brand){
        brandService.update(brand);
        return new Result(true, StatusCode.OK,"更改品牌成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        brandService.delete(id);
        return new Result(true, StatusCode.OK,"删除品牌成功");
    }

    @GetMapping("/searchPage/{pageNum}/{pageSize}")
    public Result searchPage(@RequestParam Map<String,String> paramMap,@PathVariable int pageNum,@PathVariable int pageSize){
        Page<Brand> page = brandService.searchPage(paramMap,pageNum,pageSize);
        PageResult pr = new PageResult(page.getTotal(),page.getResult());
        return new Result(true,StatusCode.OK,"分页查询成功",pr);
    }

    @GetMapping("/cate/{cateName}")
    public Result findByCateName(@PathVariable String cateName){
        List<Brand> brandList = brandService.findByCateName(cateName);
        return new Result(true,StatusCode.OK,"根据分类名查询品牌列表成功",brandList);
    }

}
