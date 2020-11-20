package com.peixian.listener;

import com.peixian.goods.feign.SkuFeign;
import com.peixian.goods.pojo.Sku;
import com.peixian.service.EsManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
@Component
@RabbitListener(queues = "search_add_queue")
public class GoodsUpListener {

    @Autowired
    private EsManagerService esManagerService;

    @RabbitHandler
    public void msgHandle(String spuId){
        esManagerService.importSkuBySpuId(spuId);
    }

}
