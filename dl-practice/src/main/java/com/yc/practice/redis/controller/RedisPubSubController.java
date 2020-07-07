package com.yc.practice.redis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.core.redispractice.entity.RedisPubSub;
import com.yc.practice.redis.service.RedisPubSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:发布订阅 控制层
 *
 * @Author: xieyc && 紫色年华
 * @Date 2020-01-29
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/redisPractice/redisPubSub")
@Slf4j
public class RedisPubSubController {

    private final RedisPubSubService iRedisPubSubService;

    @Autowired
    public RedisPubSubController(RedisPubSubService iRedisPubSubService) {
        this.iRedisPubSubService = iRedisPubSubService;
    }

    /**
     * 消息发布
     */
    @PostMapping("/sendMessage")
    public void sendMessage() {
        iRedisPubSubService.sendMessage();
    }

    /**
     * 发布订阅信息分页查询
     *
     * @param page 分页信息
     * @return page
     */
    @GetMapping("/page")
    public Page<RedisPubSub> pubSubPage(Page<RedisPubSub> page) {
        return iRedisPubSubService.pubSubPage(page);
    }

}
