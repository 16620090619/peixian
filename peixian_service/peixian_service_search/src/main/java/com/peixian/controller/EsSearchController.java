package com.peixian.controller;

import com.peixian.pojo.Result;
import com.peixian.service.EsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gzj
 * @description
 * @date 2020/11/19
 */
@RestController
@RequestMapping("/search")
public class EsSearchController {

    @Autowired
    private EsSearchService esSearchService;

    @GetMapping("/list")
    public Map search(@RequestParam Map<String,String> searchMap){
        return esSearchService.search(searchMap);
    }

}
