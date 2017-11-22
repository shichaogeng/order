package com.dongnaoedu.service;

import com.dongnaoedu.vo.GoodTransferVo;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2017-11-21 16:37
 */
@Service
@Qualifier("mq")
public class MqMode  implements IProDepot {

    @Value("${order.depot.amount.key}")
    private String DEPOT_KEY;

    @Value("${order.depot.amount.exchange}")
    private String DEPOT_EXCHANGE;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static Gson gson = new Gson();

    public void processDepot(String goodsId, int amount) {
        GoodTransferVo goodTransferVo = new GoodTransferVo();
        goodTransferVo.setGoodsId(goodsId);
        goodTransferVo.setChangeAmount(amount);
        goodTransferVo.setInOrOut(false);
        String goods = gson.toJson(goodTransferVo);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);//持久化的投递方式
        rabbitTemplate.send(DEPOT_EXCHANGE, DEPOT_KEY,
                new Message(goods.getBytes(), messageProperties));
    }
}
