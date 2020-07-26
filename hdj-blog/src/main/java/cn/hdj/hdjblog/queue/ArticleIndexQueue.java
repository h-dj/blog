package cn.hdj.hdjblog.queue;

import cn.hdj.hdjblog.constaint.RabbitMqConstants;
import cn.hdj.hdjblog.elasticsearch.ArticleRepository;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 20:10
 * @description: 文章索引队列
 */
@Component
@Slf4j
public class ArticleIndexQueue {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;


    /**
     * 更新文档
     *
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConstants.REFRESH_ES_INDEX_QUEUE)
    public void refreshEsIndex(Channel channel, Message message) throws IOException {
        try {
            log.info("刷新索引");
            List<ArticleDO> list = articleService.list(Wrappers.<ArticleDO>lambdaQuery()
                    .eq(ArticleDO::getStatus, 1)
            );
            if (CollectionUtil.isNotEmpty(list)) {
                articleRepository.refreshIndexBulk(list);
            }
            log.info("结束 刷新索引");
        } finally {
            //手动确认消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
