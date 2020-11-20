package com.peixian.controller;

import com.peixian.pojo.Result;
import com.peixian.pojo.StatusCode;
import com.peixian.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
@RestController
@RequestMapping("/manage")
public class EsManagerController {

    @Autowired
    private EsManagerService esManagerService;

    @DeleteMapping("/delete")
    public Result deleteIndexAndMapping(){
        esManagerService.deleteIndexAndMapping();
        return new Result(true, StatusCode.OK,"删除索引成功");
    }

    @PostMapping("/createIndex")
    public Result createIndexAndMapping(){
        esManagerService.createIndexAndMapping();
        return new Result(true, StatusCode.OK,"创建索引成功");
    }

    @PostMapping("/importAll")
    public Result importAll(){
        esManagerService.importAll();
        return new Result(true, StatusCode.OK,"导入全部数据成功");
    }


    @PostMapping("/deleteBySpuId/{spuId}")
    public Result deleteBySpuId(@PathVariable String spuId){
        esManagerService.deleteBySpuId(spuId);
        return new Result(true, StatusCode.OK,"导入全部数据成功");
    }


}
