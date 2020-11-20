package com.peixian.listener;

import com.peixian.service.EsManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gzj
 * @description
 * @date 2020/11/18
 */
@Component
@RabbitListener(queues = "search_reduce_queue")
public class GoodsDownListener {

    @Autowired
    private EsManagerService esManagerService;

    @RabbitHandler
    public void msgHandle(String spuId){
        esManagerService.deleteBySpuId(spuId);
    }

}
