package com.dongnaoedu.service.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2017-11-21 16:37
 */
@Service
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private Logger logger = LoggerFactory.getLogger(ConfirmCallback.class);

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("消息确认发送给mq成功");
        } else {
            //处理失败的消息
            /**
             * 一般发送的失败设计：
             * 先重复发送三次，如果都发送失败，存到数据库中
             * 编写一个定时任务程序，开启新的线程定时处理数据库中发送失败的任务。
             */
            logger.info("消息发送给mq失败,考虑重发:"+cause);
        }
    }
}
