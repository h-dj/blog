package cn.hdj.hdjblog.queue;

import cn.hdj.hdjblog.constaint.EsConstaints;
import cn.hdj.hdjblog.constaint.RabbitMqConstants;
import cn.hdj.hdjblog.elasticsearch.ArticleRepository;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.dto.ArticleDTO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initIndex() {
//        rabbitTemplate.convertAndSend(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE, RabbitMqConstants.REFRESH_ES_INDEX_QUEUE, "init index");
    }

    /**
     * 更新文档
     *
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConstants.REFRESH_ES_INDEX_QUEUE)
    public void refreshEsIndex(Channel channel, Message message) throws IOException, IllegalAccessException {
        try {
            log.info("刷新索引");
            List<ArticleDO> list = articleService.list(Wrappers.<ArticleDO>lambdaQuery()
                    .eq(ArticleDO::getStatus, 1)
            );
            if (CollectionUtil.isNotEmpty(list)) {
                List<ArticleDTO> articleList = list.stream()
                        .map(v -> {
                            ArticleDTO articleDTO = new ArticleDTO();
                            BeanUtil.copyProperties(v, articleDTO);
                            return articleDTO;
                        })
                        .collect(Collectors.toList());
                articleRepository.importAll(EsConstaints.ES_INDEX_ARTICLE, false, articleList);
            }
            log.info("结束 刷新索引");
        } finally {
            //手动确认消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
